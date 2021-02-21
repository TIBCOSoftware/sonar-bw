/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.check.process;


import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import com.tibco.utils.bw6.model.Process;
import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw6.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Transition;
import java.util.List;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = CheckpointAfterHttpCheck.RULE_KEY, name = "Checkpoint after HTTP Activities Check", priority = Priority.MAJOR, description = "This rule checks the placement of a Checkpoint activity within a process. When placing your checkpoint in a process, be careful with certain types of process starters or incoming events, so that a recovered process instance does not attempt to access resources that no longer exist. For example, consider a process with an HTTP process starter that takes a checkpoint after receiving a request but before sending a response. In this case, when the engine restarts after a crash, the recovered process instance cannot respond to the request since the HTTP socket is already closed. As a best practice, do not place Checkpoint activity right after or in parallel path to HTTP activities.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class CheckpointAfterHttpCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(CheckpointAfterHttpCheck.class);
    public static final String RULE_KEY = "CheckpointProcessHTTP";
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
            if (t.getFromActivity() != null && t.getFromActivity().getType().contains("bw.http.")) {
                if (onlyOneViolation) {
                    reportIssueOnFile("The process [" + activity.getProcess().getBasename() + "] has a Checkpoint activity ["+activity+"] placed after HTTP activity or in a parallel flow to a HTTP activity.",XmlHelper.getLineNumber(t.getFromActivity().getNode()));
                    onlyOneViolation = false;
                }
            } else if (t.getFromActivity() != null){
                checkPreviousActivities(t.getFromActivity());
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
