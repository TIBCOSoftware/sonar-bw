/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw6.check.process;


import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Process;
import com.tibco.utils.bw6.model.Transition;
import java.util.List;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = CheckpointAfterJDBCCheck.RULE_KEY, name = "Checkpoint after JDBC Query Activity Check", priority = Priority.MAJOR, description = "This rule checks the placement of a Checkpoint activity within a process. Do not place checkpoint after or in a parallel flow of Query activities or idempotent activities. Database operations such as Update, Insert and Delete are considered non-idempotent operations. You should always place a checkpoint immediately after any database insert or update activity to persist the response. However, for queries, there is no need to place checkpoints")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class CheckpointAfterJDBCCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(CheckpointAfterJDBCCheck.class);
    public static final String RULE_KEY = "CheckpointProcessJDBC";
    private boolean onlyOneViolation = true;

    @Override
        protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        process.getActivities().stream().filter((activity) -> (activity.getType() != null && activity.getType().equals("bw.internal.checkpoint"))).map((activity) -> {
            LOG.debug("Checkpoint activity detected");
            return activity;
        }).forEachOrdered((activity) -> {
            checkPreviousActivities(activity);
        });
        LOG.debug("Validation ended for rule: " + RULE_KEY);
    }

    private void checkPreviousActivities(Activity activity) {
        List<Transition> incomingTransitions = activity.getInputTransitions();

        LOG.debug("Incoming transitions: " + incomingTransitions);
        incomingTransitions.forEach((t) -> {
            if (t.getFromActivity() != null && t.getFromActivity().getType().contains("bw.jdbc.JDBCQuery")) {
                if (onlyOneViolation) {
                    reportIssueOnFile("The process [" + activity.getProcess().getBasename() +"]  has a Checkpoint activity  ["+t.getFromActivity().getName()+"] placed after a JDBC Query activity.",XmlHelper.getLineNumber(t.getFromActivity().getNode()));
                    onlyOneViolation = false;
                }
            } else {
                checkPreviousActivities(activity);
            }
        });

    }
      
    @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }
    @Override
    public Logger getLogger() {
       return LOG;
    }
}
