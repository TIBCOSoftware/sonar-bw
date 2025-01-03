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
import static com.tibco.utils.bw6.constants.BwpModelConstants.BPWSEXIT;
import static com.tibco.utils.bw6.constants.BwpModelConstants.BPWSREPLY;
import static com.tibco.utils.bw6.constants.BwpModelConstants.BPWSRETHROW;
import static com.tibco.utils.bw6.constants.BwpModelConstants.BPWSTHROW;
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Process;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;

@Rule(key = LastActivityAndEndActivityCheck.RULE_KEY, name = "Last activity Is An end activity", priority = Priority.INFO, description = "This rule checks all flows are finished properly using an end activity")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.INFO)
public class LastActivityAndEndActivityCheck extends AbstractProcessCheck {

    private static final Logger LOG = LoggerFactory.getLogger(LastActivityAndEndActivityCheck.class);
    public static final String RULE_KEY = "LastActivityAndEndActivity";
    
    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();            
        if(process != null){
  
            
            for(Activity activity : process.getActivities()){

                if((activity.getOutputTransitions().isEmpty() && !process.belongActivityToGroup(activity)) && (activity.getType() == null || !isActivityEnd(activity.getType()))){
                    reportIssueOnFile("End activity ["+activity.getName()+"] shouldn't be the last activity of the flow, as this should end with an End activity",XmlHelper.getLineNumber(activity.getNode()));
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

    private boolean isActivityEnd(String type) {
        return type != null && (type.equals(BPWSRETHROW) || type.equals("bw.internal.end") || type.equals(BPWSREPLY) || type.equals(BPWSEXIT) || type.equals(BPWSTHROW));
    }
}
