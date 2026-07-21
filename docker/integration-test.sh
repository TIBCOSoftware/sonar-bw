#!/usr/bin/env bash
#
# End-to-end integration test for the SonarQube BusinessWorks plugin.
#
# It spins up the custom SonarQube image (with the plugin), scans a real BW5
# sample project with the dockerised sonar-scanner, waits for the analysis to
# finish and verifies through the Web API that the plugin actually produced
# issues and measures.
#
# Usage:
#   docker/integration-test.sh
#
# Useful environment overrides:
#   KEEP_RUNNING=1   leave the SonarQube container up after the test
#   SQ_IMAGE=...     SonarQube image to test (default: sonarqube-custom:latest)
#   SCANNER_IMAGE=.. scanner image (default: sonarsource/sonar-scanner-cli:latest)
#   SQ_PORT=9000     host port to publish SonarQube on
#
set -euo pipefail

# --------------------------------------------------------------------------- #
# Configuration
# --------------------------------------------------------------------------- #
REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$REPO_ROOT"

SQ_IMAGE="${SQ_IMAGE:-sonarqube-custom:latest}"
SCANNER_IMAGE="${SCANNER_IMAGE:-sonarsource/sonar-scanner-cli:latest}"
SQ_PORT="${SQ_PORT:-9000}"

NETWORK="bw-it-net"
SQ_CONTAINER="bw-sonarqube-it"
PROJECT_KEY="bw5-it-sample"

SAMPLE_SRC="src/test/resources/bw/bw5/SonarSamples"
WORKSPACE="target/it-workspace"

HOST_URL="http://localhost:${SQ_PORT}"          # reached from this host
NET_URL="http://${SQ_CONTAINER}:9000"           # reached from the scanner container

ADMIN_USER="admin"
ADMIN_PASS="admin"

STARTUP_TIMEOUT="${STARTUP_TIMEOUT:-300}"       # seconds to wait for SonarQube UP
CE_TIMEOUT="${CE_TIMEOUT:-180}"                 # seconds to wait for the analysis task

log()  { printf '\033[1;34m[it]\033[0m %s\n' "$*"; }
ok()   { printf '\033[1;32m[it] ✓ %s\033[0m\n' "$*"; }
err()  { printf '\033[1;31m[it] ✗ %s\033[0m\n' "$*" >&2; }

# --------------------------------------------------------------------------- #
# Cleanup
# --------------------------------------------------------------------------- #
cleanup() {
  local rc=$?
  if [[ "${KEEP_RUNNING:-0}" == "1" ]]; then
    log "KEEP_RUNNING=1 -> leaving '${SQ_CONTAINER}' up at ${HOST_URL} (admin/admin)."
  else
    log "Tearing down containers and network..."
    docker rm -f "$SQ_CONTAINER" >/dev/null 2>&1 || true
    docker network rm "$NETWORK"  >/dev/null 2>&1 || true
  fi
  exit "$rc"
}
trap cleanup EXIT

# --------------------------------------------------------------------------- #
# Preflight
# --------------------------------------------------------------------------- #
command -v docker >/dev/null || { err "docker is not available"; exit 1; }
command -v curl   >/dev/null || { err "curl is not available";   exit 1; }
command -v jq     >/dev/null || { err "jq is not available";     exit 1; }

if ! docker image inspect "$SQ_IMAGE" >/dev/null 2>&1; then
  err "Image '$SQ_IMAGE' not found. Build it first with 'make build'."
  exit 1
fi

if [[ ! -d "$SAMPLE_SRC" ]]; then
  err "Sample project not found at '$SAMPLE_SRC'."
  exit 1
fi

# Elasticsearch inside SonarQube needs a high mmap count; warn if it's too low.
MMAP="$(cat /proc/sys/vm/max_map_count 2>/dev/null || echo 0)"
if [[ "$MMAP" -lt 262144 ]]; then
  log "WARNING: vm.max_map_count=$MMAP (<262144). SonarQube may fail to start."
  log "         Fix with: sudo sysctl -w vm.max_map_count=262144"
fi

# Remove leftovers from a previous run.
docker rm -f "$SQ_CONTAINER" >/dev/null 2>&1 || true
docker network rm "$NETWORK"  >/dev/null 2>&1 || true

# --------------------------------------------------------------------------- #
# Start SonarQube
# --------------------------------------------------------------------------- #
log "Creating network '$NETWORK'..."
docker network create "$NETWORK" >/dev/null

log "Starting SonarQube ('$SQ_IMAGE') as '$SQ_CONTAINER'..."
docker run -d --name "$SQ_CONTAINER" --network "$NETWORK" \
  -p "${SQ_PORT}:9000" \
  -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true \
  "$SQ_IMAGE" >/dev/null

log "Waiting for SonarQube to be UP (timeout ${STARTUP_TIMEOUT}s)..."
deadline=$(( SECONDS + STARTUP_TIMEOUT ))
status=""
while (( SECONDS < deadline )); do
  status="$(curl -fsS "${HOST_URL}/api/system/status" 2>/dev/null | jq -r '.status' 2>/dev/null || true)"
  if [[ "$status" == "UP" ]]; then break; fi
  if ! docker ps --format '{{.Names}}' | grep -q "^${SQ_CONTAINER}$"; then
    err "SonarQube container exited unexpectedly. Last logs:"
    docker logs --tail 40 "$SQ_CONTAINER" || true
    exit 1
  fi
  sleep 5
done
if [[ "$status" != "UP" ]]; then
  err "SonarQube did not become UP within ${STARTUP_TIMEOUT}s (last status: '${status:-none}')."
  docker logs --tail 40 "$SQ_CONTAINER" || true
  exit 1
fi
ok "SonarQube is UP."

# --------------------------------------------------------------------------- #
# Authentication token
# --------------------------------------------------------------------------- #
log "Generating an analysis token..."
TOKEN="$(curl -fsS -u "${ADMIN_USER}:${ADMIN_PASS}" \
  -X POST "${HOST_URL}/api/user_tokens/generate?name=it-$(date +%s)" \
  | jq -r '.token' 2>/dev/null || true)"
if [[ -z "$TOKEN" || "$TOKEN" == "null" ]]; then
  err "Could not generate a token (default admin credentials may have changed)."
  exit 1
fi
ok "Token generated."

# --------------------------------------------------------------------------- #
# Assemble the scanner workspace
# --------------------------------------------------------------------------- #
log "Assembling scanner workspace at '$WORKSPACE'..."
rm -rf "$WORKSPACE"
mkdir -p "$WORKSPACE"
cp -R "${SAMPLE_SRC}/." "$WORKSPACE/"
cp docker/it/sonar-project.properties "$WORKSPACE/"

# --------------------------------------------------------------------------- #
# Run the scanner
# --------------------------------------------------------------------------- #
log "Running sonar-scanner ('$SCANNER_IMAGE')..."
SCANNER_LOG="${WORKSPACE}/scanner.log"
docker run --rm --network "$NETWORK" \
  -e SONAR_HOST_URL="$NET_URL" \
  -e SONAR_TOKEN="$TOKEN" \
  -v "${REPO_ROOT}/${WORKSPACE}:/usr/src" \
  "$SCANNER_IMAGE" 2>&1 | tee "$SCANNER_LOG"
ok "Scanner finished submitting the analysis."

# --------------------------------------------------------------------------- #
# Wait for the Compute Engine task to complete
# --------------------------------------------------------------------------- #
# The dockerised scanner runs with its working dir under /tmp (not the mounted
# volume), so report-task.txt is not visible on the host; take the CE task id
# from the scanner output instead, falling back to report-task.txt if present.
CE_TASK_ID="$(grep -oE 'api/ce/task\?id=[A-Za-z0-9_-]+' "$SCANNER_LOG" | head -1 | sed 's/.*id=//')"
if [[ -z "$CE_TASK_ID" && -f "${WORKSPACE}/.scannerwork/report-task.txt" ]]; then
  CE_TASK_ID="$(grep -E '^ceTaskId=' "${WORKSPACE}/.scannerwork/report-task.txt" | cut -d= -f2-)"
fi
[[ -n "$CE_TASK_ID" ]] || { err "Could not determine the CE task id from the scanner output."; exit 1; }
log "Waiting for analysis task ${CE_TASK_ID} (timeout ${CE_TIMEOUT}s)..."
deadline=$(( SECONDS + CE_TIMEOUT ))
ce_status=""
while (( SECONDS < deadline )); do
  ce_status="$(curl -fsS -u "${ADMIN_USER}:${ADMIN_PASS}" \
    "${HOST_URL}/api/ce/task?id=${CE_TASK_ID}" | jq -r '.task.status' 2>/dev/null || true)"
  case "$ce_status" in
    SUCCESS) break ;;
    FAILED|CANCELED) err "Analysis task ended with status: $ce_status"; exit 1 ;;
  esac
  sleep 3
done
[[ "$ce_status" == "SUCCESS" ]] || { err "Analysis did not finish in time (status: ${ce_status:-none})."; exit 1; }
ok "Analysis task completed (SUCCESS)."

# --------------------------------------------------------------------------- #
# Verify results
# --------------------------------------------------------------------------- #
log "Verifying the plugin produced findings..."

ISSUES="$(curl -fsS -u "${ADMIN_USER}:${ADMIN_PASS}" \
  "${HOST_URL}/api/issues/search?componentKeys=${PROJECT_KEY}&resolved=false&ps=1" \
  | jq -r '.total' 2>/dev/null || echo 0)"

MEASURES_JSON="$(curl -fsS -u "${ADMIN_USER}:${ADMIN_PASS}" \
  "${HOST_URL}/api/measures/component?component=${PROJECT_KEY}&metricKeys=files,ncloc,violations" \
  || echo '{}')"
FILES="$(echo "$MEASURES_JSON" | jq -r '.component.measures[]? | select(.metric=="files") | .value' 2>/dev/null || echo "")"
FILES="${FILES:-0}"

log "Top rules triggered:"
curl -fsS -u "${ADMIN_USER}:${ADMIN_PASS}" \
  "${HOST_URL}/api/issues/search?componentKeys=${PROJECT_KEY}&resolved=false&facets=rules&ps=1" \
  | jq -r '.facets[]? | select(.property=="rules") | .values[] | "    \(.val): \(.count)"' 2>/dev/null || true

echo
log "Summary:  files=${FILES}  issues=${ISSUES}"

FAIL=0
if [[ "${FILES}" -lt 1 ]]; then err "Expected at least 1 analysed file, got ${FILES}."; FAIL=1; fi
if [[ "${ISSUES}" -lt 1 ]]; then err "Expected the plugin to raise at least 1 issue, got ${ISSUES}."; FAIL=1; fi

if [[ "$FAIL" -ne 0 ]]; then
  err "Integration test FAILED."
  exit 1
fi

ok "Integration test PASSED — plugin analysed the BW5 project and produced ${ISSUES} issue(s)."
log "Dashboard: ${HOST_URL}/dashboard?id=${PROJECT_KEY}"
