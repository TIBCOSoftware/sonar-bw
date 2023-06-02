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
import static com.tibco.utils.bw6.constants.BwpModelConstants.BPWSEXIT;
import static com.tibco.utils.bw6.constants.BwpModelConstants.BPWSREPLY;
import static com.tibco.utils.bw6.constants.BwpModelConstants.BPWSRETHROW;
import static com.tibco.utils.bw6.constants.BwpModelConstants.BPWSTHROW;
import com.tibco.utils.bw6.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Process;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = LastActivityAndEndActivityCheck.RULE_KEY, name = "Last activity Is An end activity", priority = Priority.INFO, description = "This rule checks all flows are finished properly using an end activity")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.INFO)
public class LastActivityAndEndActivityCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(LastActivityAndEndActivityCheck.class);
    public static final String RULE_KEY = "LastActivityAndEndActivity";
    
    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();            
        if(process != null){
  
            
            for(Activity activity : process.getActivities()){
                if(activity.getOutputTransitions().isEmpty() && !process.belongActivityToGroup(activity)){
                    if(activity.getType() == null || !isActivityEnd(activity.getType())){
                        reportIssueOnFile("End activity ["+activity.getName()+"] shouldn't be the last activity of the flow, as this should end with an End activity",XmlHelper.getLineNumber(activity.getNode()));                      
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

    private boolean isActivityEnd(String type) {
        return type != null && (type.equals(BPWSRETHROW) || type.equals("bw.internal.end") || type.equals(BPWSREPLY) || type.equals(BPWSEXIT) || type.equals(BPWSTHROW));
    }
}
