package com.tibco.sonar.plugins.bw6.check.process;

import java.util.List;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw6.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.EventSource;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = JMSReceiverPlusConfirmCheck.RULE_KEY, name = "Confirm Activity presence Check", priority = Priority.INFO, description = "Confirm activity should cover all OK flows with a JMS Receiver if  CLIENT ACK Mode is Selected.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.INFO)
public class JMSReceiverPlusConfirmCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(JMSReceiverPlusConfirmCheck.class);

    public static final String RULE_KEY = "JMSReceiverPlusConfirm";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        List<Activity> activities = processSource.getProcessModel().getActivities();
        
        
        
        activities.forEach((activity) -> {
            
            
            if (activity.getType() != null && activity.getType().contains("bw.jms.receive")) {
                LOG.debug("JMS Receive Message activity detected");
                if("client".equals( activity.getProperty("ackMode"))){
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
            }
        });
        
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
