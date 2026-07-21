SHELL := /bin/bash

.PHONY: build docker-run check integration-test it it-clean

build:
	mvn clean package
	rm -rf docker/extensions/*
	cp target/sonar-bw-plugin-*.jar docker/extensions
	docker build --tag=sonarqube-custom docker/.

docker-run:
	docker run -p 9000:9000 -ti sonarqube-custom:latest

# End-to-end test: (re)build the plugin image, spin up SonarQube, scan the BW5
# sample project with the dockerised scanner and verify the plugin produced
# issues + measures via the Web API. Use KEEP_RUNNING=1 to keep SonarQube up.
integration-test it: build
	./docker/integration-test.sh

# Stop the integration-test SonarQube container / network / workspace.
it-clean:
	-docker rm -f bw-sonarqube-it 2>/dev/null
	-docker network rm bw-it-net 2>/dev/null
	rm -rf target/it-workspace

check:
	mvn clean verify sonar:sonar \
	  -Dsonar.projectKey=bw6-plugin \
	  -Dsonar.projectName='bw6-plugin' \
	  -Dsonar.host.url=http://localhost:9000 \
	  -Dsonar.token=sqp_f9b00fec0e4ea0aac87cd1682bae48b8a082d250