/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.check.process;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw6.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Process;
import com.tibco.utils.bw6.model.Transition;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = MultipleTransitionCheck.RULE_KEY, name = "Multiple Transitions Check", priority = Priority.INFO, description = "EMPTY activity should be used if you want to join multiple transition flows. For example, there are multiple transitions out of an activity and each transition takes a different path in the process. In this scenario you can create a transition from the activity at the end of each path to an Empty activity to resume a single flow of execution in the process. This rule checks whether multiple transitions from an activity in a parallel flow merge into EMPTY activity")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.INFO)
public class MultipleTransitionCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(MultipleTransitionCheck.class);
    public static final String RULE_KEY = "MultipleTransitions";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        Map<String, Transition> map = process.getTransitions();
        Iterator<Map.Entry<String, Transition>> it = map.entrySet().iterator();
        Set<String> set = new HashSet<>();
        boolean activityFlag = false;
        while (it.hasNext()) {
            Map.Entry<String, Transition> pair = it.next();
            if (pair.getValue().getTo() != null) {
                if (!set.add(pair.getValue().getTo())) {
                    for (Activity activity : process.getActivities()) {
                        if (activity.getName().equals(pair.getValue().getTo())) {
                            if (activity.getType() != null) {
                                reportIssueOnFile("There are multiple transitions converging into activity " + pair.getValue().getTo() + ". When there are multiple transitions in a parallel flow, they should converge preferably in a EMPTY activity. This ensures that following activities after the EMPTY activity will have all the outputs available from parallel paths.",XmlHelper.getLineNumber(activity.getNode()));
                                activityFlag = true;
                            }
                        }
                    }
                    activityFlag = !activityFlag;
                }
            }

            //ToDo: Need to fix this issue...for groups there will always be multiple transitions converging as done a fix for Checkpoint in transaction for which GroupStart = GroupEnd = GroupName 
            /*if(activityFlag){
				Violation violation = new DefaultViolation(getRule(),
						1,
						"There are multiple transitions converging into group "+pair.getValue().getTo()+". When there are multiple transitions in a parallel flow, they should converge preferably in a EMPTY activity. This ensures that following activities after the EMPTY activity will have all the outputs available from parallel paths.");
				processSource.addViolation(violation);
				activityFlag = false;
			}*/
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
