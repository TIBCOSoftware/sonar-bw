package com.tibco.sonar.plugins.bw6.check.process;

import java.util.List;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw.model.Activity;
import com.tibco.utils.bw.model.EventSource;

@Rule(key = JMSHardCodeCheck.RULE_KEY, name = "JMS HardCoded Check", priority = Priority.MAJOR, description = "This rule checks JMS activities for hardcoded values for fields Timeout, Destinaton, Reply to Destination, Message Selector, Polling Interval. Use Process property or Module property.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class JMSHardCodeCheck extends AbstractProcessCheck {

    public static final String RULE_KEY = "JMSHardCoded";

    @Override
    protected void validate(ProcessSource processSource) {

        List<Activity> activities = processSource.getProcessModel().getActivities();
        activities.stream().filter((activity) -> (activity.getType() != null && activity.getType().contains("bw.jms."))).forEachOrdered((activity) -> {
            detectViolation(activity);
        });

        List<EventSource> eventSources = processSource.getProcessModel().getEventSources();

        eventSources.stream().filter((eventSource) -> (eventSource.getType() != null && eventSource.getType().contains("bw.jms."))).forEachOrdered((eventSource) -> {
            detectViolation(eventSource);
        });
    }

    public void detectViolation(Activity activity) {
        if (activity.hasProperty("destinationName")) {
            reportIssueOnFile("The Destination Name setting in the JMS activity " + activity.getName() + " is assigned a hardcoded value. It should be defined as Process property or Module property.");
        }
        if (activity.hasProperty("replyToDestination") || activity.hasProperty("nullreplyToQueue")) {
            reportIssueOnFile("The Reply to Destination setting in the JMS activity " + activity.getName() + " is assigned a hardcoded value. It should be defined as Process property or Module property.");
        }
        if (activity.hasProperty("maxSessions")) {
            reportIssueOnFile("The Max Sessions setting in the JMS activity " + activity.getName() + " is assigned a hardcoded value. It should be defined as Process property or Module property.");
        }
        if (activity.hasProperty("messageSelector")) {
            reportIssueOnFile("The Message Selector setting in the JMS activity " + activity.getName() + " is assigned a hardcoded value. It should be defined as Process property or Module property.");
        }
        if (activity.hasProperty("receiveTimeout")) {
            reportIssueOnFile("The Polling Interval setting in the JMS activity " + activity.getName() + " is assigned a hardcoded value. It should be defined as Process property or Module property.");
        }
        if (activity.hasProperty("destinationName")) {
            reportIssueOnFile("The Destination Name setting in the JMS Event Source activity " + activity.getName() + " is assigned a hardcoded value. It should be defined as Process property or Module property.");
        }
        if (activity.hasProperty("messageSelector")) {
            reportIssueOnFile("The Message Selector setting in the JMS Event Source activity " + activity.getName() + " is assigned a hardcoded value. It should be defined as Process property or Module property.");
        }
        if (activity.hasProperty("receiveTimeout")) {
            reportIssueOnFile("The Polling Interval setting in the JMS Event Source activity " + activity.getName() + " is assigned a hardcoded value. It should be defined as Process property or Module property.");
        }

    }

    @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }
}
