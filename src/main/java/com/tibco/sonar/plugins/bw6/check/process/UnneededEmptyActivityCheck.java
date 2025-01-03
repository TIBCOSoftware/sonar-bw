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
import com.tibco.utils.bw6.constants.BwpModelConstants;
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Process;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;

@Rule(key = UnneededEmptyActivityCheck.RULE_KEY, name = "Unneeded Empty Activity", priority = Priority.MINOR, description = "This rule checks for empty activities that only have one input transition and output tranistion so they are not providing any value")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class UnneededEmptyActivityCheck extends AbstractProcessCheck {

    private static final Logger LOG = LoggerFactory.getLogger(UnneededEmptyActivityCheck.class);
    public static final String RULE_KEY = "UnneededEmptyActivity";
    
    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();            
        if(process != null){
            for(Activity activity : process.getActivitiesByType(BwpModelConstants.BPWSEMPTY)){
                LOG.debug("Activty type ["+activity.getType() + "] - ["+activity.getName()+"]");
                if(activity.getInputTransitions().size() == 1 && activity.getOutputTransitions().size() == 1 && !"ERROR".equals(activity.getInputTransitions().get(0).getConditionType())){
                    reportIssueOnFile("Empty activity ["+activity.getName()+"] should be avoided as it is not helping to make understandable the flow",XmlHelper.getLineNumber(activity.getNode()));
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
}
