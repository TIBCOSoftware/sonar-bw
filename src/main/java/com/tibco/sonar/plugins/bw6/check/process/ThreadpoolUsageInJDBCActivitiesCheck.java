/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.check.process;


import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw6.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Process;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = ThreadpoolUsageInJDBCActivitiesCheck.RULE_KEY, name = "ThreadPool Resource Usage in JDBC Activities", priority = Priority.MAJOR, description = "This rule check if you are setting up a ThreadPool Resource to your JDBC Activities to handle the increasing number of threads because of JDBC Activities")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class ThreadpoolUsageInJDBCActivitiesCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(ThreadpoolUsageInJDBCActivitiesCheck.class);
    public static final String RULE_KEY = "ThreadpoolUsageInJDBCActivities";

    @Override
        protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        for(Activity activity: process.getActivities()){
            if(activity.getType() != null && activity.getType().contains("bw.jdbc")){
                LOG.debug("JDBC Activity detected: "+activity.getName());
                if(!activity.getProperties().containsKey("threadPool")){
                        reportIssueOnFile("Activity ["+activity.getName()+"] in process [" + process.getName() +"]  has not configured Thread Pool Resource.",XmlHelper.getLineNumber(activity.getNode()));
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
