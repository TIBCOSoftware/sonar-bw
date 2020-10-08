/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
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

@Rule(key = JMSRequestReplyNonPersistentCheck.RULE_KEY, name = "JMS Request/Reply shoud use NON-PERSISTENT messages", priority = Priority.INFO, description = "JMS Request/Reply are for synchronous communication using JMS so it should use NON-PERSISTENT messages to increase performance")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.INFO)
public class JMSRequestReplyNonPersistentCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(JMSRequestReplyNonPersistentCheck.class);

    public static final String RULE_KEY = "JMSRequestReplyNonPersistent";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        List<Activity> activities = processSource.getProcessModel().getActivitiesByType("bw.jms.requestreply");
        activities.forEach((activity) -> {
                if(!activity.hasProperty("deliveryMode")){
                    reportIssueOnFile("PERSISTENT Delivery Mode is set in the JMS activity [" + activity.getName() + "].  Avoid using PERSISTENT message with Request/Reply communication to increase performance.",XmlHelper.getLineNumber(activity.getNode()));
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
