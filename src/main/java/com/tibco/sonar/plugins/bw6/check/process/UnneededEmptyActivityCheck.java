/*
 * Copyright (C) 2016-2023. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
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
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = UnneededEmptyActivityCheck.RULE_KEY, name = "Unneeded Empty Activity", priority = Priority.MINOR, description = "This rule checks for empty activities that only have one input transition and output tranistion so they are not providing any value")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class UnneededEmptyActivityCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(UnneededEmptyActivityCheck.class);
    public static final String RULE_KEY = "UnneededEmptyActivity";
    
    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();            
        if(process != null){
            for(Activity activity : process.getActivitiesByType(BwpModelConstants.BPWSEMPTY)){
                LOG.debug("Activty type ["+activity.getType() + "] - ["+activity.getName()+"]");
                
                if(activity.getInputTransitions().size() == 1 && activity.getOutputTransitions().size() == 1){
                    if(!"ERROR".equals(activity.getInputTransitions().get(0).getConditionType())){
                        reportIssueOnFile("Empty activity ["+activity.getName()+"] should be avoided as it is not helping to make understandable the flow",XmlHelper.getLineNumber(activity.getNode()));
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
}
