/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.process;

import java.util.HashMap;
import java.util.Map;

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

        final Process process = processSource.getProcessModel();
        final Map<String, Integer>usages = recordUsages(process);

        for (Map.Entry<String, Integer>entry : usages.entrySet()) {
        	// If there are more than one inflows to an activity then we have an issue
        	if (entry.getValue().intValue() > 1) {
        		// Handle group activities differently to base activities
        		if (entry.getKey().startsWith(":")) {
            		final Activity group = process.getActivityByName(entry.getKey().substring(1));
            		
                    if (!isRuleDisabled(group)) {
                    	reportIssueOnFile("There are multiple transitions converging into group " + group.getName() + ". When there are multiple transitions in a parallel flow, they should converge preferably in a EMPTY activity. This ensures that following activities after the EMPTY activity will have all the outputs available from parallel paths.",XmlHelper.getLineNumber(group.getNode()));
                    }
        		} else {
            		final Activity activity = process.getActivityByName(entry.getKey());

            		if (!isRuleDisabled(activity)) {
            			reportIssueOnFile("There are multiple transitions converging into activity " + activity.getName() + ". When there are multiple transitions in a parallel flow, they should converge preferably in a EMPTY activity. This ensures that following activities after the EMPTY activity will have all the outputs available from parallel paths.",XmlHelper.getLineNumber(activity.getNode()));
            		}
        		}
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

    private Map<String, Integer> recordUsages(Process process) {
    	final HashMap<String, Integer>usages = new HashMap<>();
        final Integer defaultCount = Integer.valueOf(0);

        for (Map.Entry<String, Transition>entry : process.getTransitions().entrySet()) {
    		Transition trans = entry.getValue();
    		if (trans.getTo() != null) {
	    		final String toName = trans.getFrom() == null ? ":" + trans.getTo() : trans.getTo();
	    		final Activity toActivity = process.getActivityByName(trans.getTo());
	    		
	    		// If the to activity is not an empty step then track its usage
	    		final String activityType = toActivity.getType();
	    		if (activityType != null && !"bpws:empty".equals(activityType)) {
		    		Integer count = usages.getOrDefault(toName, defaultCount);
		    		usages.put(toName, Integer.valueOf(count.intValue() + 1));
	    		}
    		}
        }
        
        return usages;
    }
}
