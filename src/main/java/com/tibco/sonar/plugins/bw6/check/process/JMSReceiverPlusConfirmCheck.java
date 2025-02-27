/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.process;

import java.util.List;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;

@Rule(key = JMSReceiverPlusConfirmCheck.RULE_KEY, name = "Confirm Activity presence Check", priority = Priority.INFO, description = "Confirm activity should cover all OK flows with a JMS Receiver if  CLIENT ACK Mode is Selected.",tags = {"bug"})
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.INFO)
public class JMSReceiverPlusConfirmCheck extends AbstractProcessCheck {

    private static final Logger LOG = LoggerFactory.getLogger(JMSReceiverPlusConfirmCheck.class);

    public static final String RULE_KEY = "JMSReceiverPlusConfirm";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        List<Activity> activities = processSource.getProcessModel().getActivities();
        
        
        
        activities.forEach(activity -> {
            
            
            if (activity.getType() != null && activity.getType().contains("bw.jms.receive")) {
                LOG.debug("JMS Receive Message activity detected");
                if("client".equals( activity.getProperty("ackMode"))){
                    checkForConfirmActivity(activity);


                }
            }
        });
        
        LOG.debug("Validation ended for rule: " + RULE_KEY);
    }

    private void checkForConfirmActivity(Activity activity) {
        List<List<Activity>> flowList = activity.checkFlowArray(true);
        if(flowList != null){
            for(List<Activity> flow : flowList){
                Activity last = activity;
                boolean found = false;
                for(Activity act : flow){
                    last = act;
                    if("bw.generalactivities.confirm".equals(act.getType())){
                        found=true;
                    }
                }
                if(!found){
                    reportIssueOnFile("Confirm activitites should cover all OK flows to be ready to consume the message not depending on the logic flow",XmlHelper.getLineNumber(last.getNode()));
                }
            }

        }
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
