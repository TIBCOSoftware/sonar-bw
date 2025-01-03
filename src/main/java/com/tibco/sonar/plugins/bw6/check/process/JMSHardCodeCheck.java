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

@Rule(key = JMSHardCodeCheck.RULE_KEY, name = "JMS HardCoded Check", priority = Priority.MINOR, description = "This rule checks JMS activities for hardcoded values for fields Timeout, Destinaton, Reply to Destination, Message Selector, Polling Interval. Use Process property or Module property.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class JMSHardCodeCheck extends AbstractProcessCheck {

    private static final Logger LOG = LoggerFactory.getLogger(JMSHardCodeCheck.class);
    public static final String RULE_KEY = "JMSHardCoded";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        List<Activity> activities = processSource.getProcessModel().getActivities();
        activities.stream().filter(activity -> (activity.getType() != null && activity.getType().contains("bw.jms."))).forEachOrdered(this::detectIssue);

        LOG.debug("Validation ended for rule: " + RULE_KEY);
    }

    public void detectIssue(Activity activity) {
        if (activity.hasProperty("replyToDestination") || activity.hasProperty("nullreplyToQueue")) {
            reportIssueOnFile(String.format("The Reply to Destination setting in the JMS activity [%s] is assigned a hardcoded value. It should be defined as Process property or Module property.", activity.getName()),XmlHelper.getLineNumber(activity.getNode()));
        }
        if (activity.hasProperty("maxSessions") && activity.hasProperty("ackMode")) {
            reportIssueOnFile(String.format("The Max Sessions setting in the JMS activity [%s] is assigned a hardcoded value. It should be defined as Process property or Module property.", activity.getName()),XmlHelper.getLineNumber(activity.getNode()));
        }
        if (activity.hasProperty("destinationName")) {
            reportIssueOnFile(String.format("The Destination Name setting in the JMS activity [%s] is assigned a hardcoded value. It should be defined as Process property or Module property.", activity.getName()),XmlHelper.getLineNumber(activity.getNode()));
        }
        if (activity.hasProperty("messageSelector")) {
            reportIssueOnFile(String.format("The Message Selector setting in the JMS activity [%s] is assigned a hardcoded value. It should be defined as Process property or Module property.", activity.getName()),XmlHelper.getLineNumber(activity.getNode()));
        }        
        if (activity.hasProperty("receiveTimeout")) {
            reportIssueOnFile(String.format("The Polling Interval setting in the JMS activity [%s] is assigned a hardcoded value. It should be defined as Process property or Module property.", activity.getName()),XmlHelper.getLineNumber(activity.getNode()));
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
