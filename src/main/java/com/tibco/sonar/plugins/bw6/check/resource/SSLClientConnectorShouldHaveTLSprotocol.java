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
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(
        key = SSLClientConnectorShouldHaveTLSprotocol.RULE_KEY,
        name = "SSLClient Connector should use recommended TLS protocol",
        description = "SSLClient Connector should use recommended TLS protocol to secure all communications between a client and a server",
        tags = {"security"},
        priority = Priority.MINOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class SSLClientConnectorShouldHaveTLSprotocol extends AbstractResourceCheck {

    public static final String RULE_KEY = "SSLClientConnectorShouldHaveTLSprotocol";
    private static final Logger LOG = LoggerFactory.getLogger(SSLClientConnectorShouldHaveTLSprotocol.class);
    @Override
    public void validate(SharedResourceSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        SharedResource resource = resourceXml.getResource();
        LOG.debug("Checking project resource files");

        if (resource != null) {
            LOG.debug("Resource Type [" + resource.getType() + "]");
            if ("sslclient:SSLClientConfiguration".equals(resource.getType())) {
                LOG.debug("Detected resource as SSLClient Connector. Continue to check details");
                if ((resource.getParameterByName("sslProtocol") != null) && ("SSLv3".equals(resource.getParameterByName("sslProtocol").getValue()))) {
                    reportIssueOnFile("Shared resource of type SSLClient Connector [" + resource.getName() + "] is not using recommended TLS protocol");
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
    public Logger getLogger() {
        return LOG;
    }

}