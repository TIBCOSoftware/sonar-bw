/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw5.check.process;

import com.tibco.sonar.plugins.bw5.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw5.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw5.source.ProcessSource;
import com.tibco.utils.bw5.model.Process;
import com.tibco.utils.bw5.model.Transition;
import com.tibco.utils.common.helper.XmlHelper;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
    
@Rule(key = TransitionLabelCheck.RULE_KEY, name = "Transition Labels Check", priority = Priority.MINOR, description = "This rule checks whether the transitions with the type 'Success With Condition' (XPath) have a proper label. This will improve code readability")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class TransitionLabelCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(TransitionLabelCheck.class);
    public static final String RULE_KEY = "TransitionLabels";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        List<Transition> transitions = process.getTransitions();
        Iterator<Transition> it = transitions.iterator();
        while (it.hasNext()) {
            Transition transition = it.next();
            LOG.debug("Checking transition [" + transition.getFrom()+"-"+transition.getTo() + "] with condition type [" + transition.getConditionType() + "] and label [" + transition.getLabel() + "]");
            if (transition.getConditionType() != null && transition.getConditionType().equals("xpath") &&  (transition.getLabel() == null || transition.getLabel().isEmpty())) {
                reportIssueOnFile("The transition from " + transition.getFrom() + " to " + transition.getTo() + " doesn't have a proper label", XmlHelper.getLineNumber(transition.getNode()));
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
