/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.resource;

import com.tibco.sonar.plugins.bw6.check.AbstractResourceCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.SharedResourceSource;
import com.tibco.utils.bw6.model.SharedResource;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;


@Rule(
        key = JMSConnectorShouldHaveConfidentiality.RULE_KEY,
        name = "JMS Connector should have confidentiality",
        description = "Shared resource of type JMS Connector should have set security confidentiality",
        tags = {"security"},
        priority = Priority.MINOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class JMSConnectorShouldHaveConfidentiality extends AbstractResourceCheck {

    public static final String RULE_KEY = "JMSConnectorShouldHaveConfidentiality";
    private static final Logger LOG = Loggers.get(JMSConnectorShouldHaveConfidentiality.class);

    @Override
    public void validate(SharedResourceSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        SharedResource resource = resourceXml.getResource();
        LOG.debug("Checking project resources files");

        if (resource != null) {
            LOG.debug("Resource Type [" + resource.getType() + "]");
            if ("jms:JMSConnectionFactory".equals(resource.getType())) {
                LOG.debug("Detected resource as JMS Connector. Continue to check details");

                if (!(resource.getParameterByName("provides") != null && "confidentiality".equals(resource.getParameterByName("provides").getValue()))) {
                    reportIssueOnFile("Shared resource of type JMS Connector [" + resource.getName() + "] is not using security confidentiality");
                }
            }
        }

        LOG.debug("Finished rule: " + this.getClass());
    }

    @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }

    @Override
    public org.sonar.api.utils.log.Logger getLogger() {
        return LOG;
    }

}
