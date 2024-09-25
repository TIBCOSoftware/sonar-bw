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
import com.tibco.utils.bw6.model.EventSource;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = JMSAcknowledgementModeCheck.RULE_KEY, name = "JMS Acknowledgement Mode Check", priority = Priority.INFO, description = "This rule checks the acknowledgement mode used in JMS activities. Avoid using Auto Acknowledgement to minimize the risk of data loss.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.INFO)
public class JMSAcknowledgementModeCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(JMSAcknowledgementModeCheck.class);

    public static final String RULE_KEY = "JMSAcknowledgementMode";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        List<Activity> activities = processSource.getProcessModel().getActivities();
        activities.forEach((activity) -> {
            if (activity.getType() != null && activity.getType().contains("bw.jms.getmsg")) {
                LOG.debug("JMS Get Message activity detected");
                if(!activity.hasProperty("ackMode")){
                    reportIssueOnFile("Auto Acknowledgement mode is set in the JMS activity " + activity.getName() + ".  Avoid using Auto Acknowledgement to minimize the risk of data loss.",XmlHelper.getLineNumber(activity.getNode()));
                }
            }
        });
        
        List<EventSource> eventSources = processSource.getProcessModel().getEventSources();
        eventSources.forEach((eventSource) -> {
            if (eventSource.getType() != null && (eventSource.getType().contains("bw.jms.signalin") || eventSource.getType().contains("bw.jms.receive"))) {
                if(!eventSource.hasProperty("ackMode")){
                    reportIssueOnFile("Auto Acknowledgement mode is set in the JMS activity " + eventSource.getName() + ".  Avoid using Auto Acknowledgement to minimize the risk of data loss.",XmlHelper.getLineNumber(eventSource.getNode()));
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
