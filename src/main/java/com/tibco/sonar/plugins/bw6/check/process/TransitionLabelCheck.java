/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.process;

import java.util.Iterator;
import java.util.Map;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw6.model.Process;
import com.tibco.utils.bw6.model.Transition;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
    
@Rule(key = TransitionLabelCheck.RULE_KEY, name = "Transition Labels Check", priority = Priority.MINOR, description = "This rule checks whether the transitions with the type 'Success With Condition' (XPath) have a proper label. This will improve code readability")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class TransitionLabelCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(TransitionLabelCheck.class);
    public static final String RULE_KEY = "TransitionLabels";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        Map<String, Transition> transitions = process.getTransitions();
        Iterator<Map.Entry<String, Transition>> it = transitions.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Transition> pair = it.next();
            Transition transition = pair.getValue();
            LOG.debug("Checking transition [" + transition.getName() + "] with condition type [" + transition.getConditionType() + "] and label [" + transition.getLabel() + "]");
            if (transition.getConditionType() != null && transition.getConditionType().equals("SUCCESSWITHCONDITION") &&  transition.getLabel() == null) {
                reportIssueOnFile("The transition from " + transition.getFrom() + " to " + transition.getTo() + " doesn't have a proper label", transition.getLineNumber());
            }
        }
        LOG.debug("Validation ended for rule: " + RULE_KEY);
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
