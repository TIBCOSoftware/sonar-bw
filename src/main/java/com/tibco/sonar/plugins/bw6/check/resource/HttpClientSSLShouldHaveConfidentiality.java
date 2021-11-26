/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.check.resource;

import com.tibco.sonar.plugins.bw6.check.AbstractResourceCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.SharedResourceSource;
import com.tibco.utils.bw6.model.SharedResource;
import com.tibco.utils.bw6.model.SharedResourceParameter;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(
        key = HttpClientSSLShouldHaveConfidentiality.RULE_KEY,
        name = "HTTP client should have confidentiality",
        description = "HTTP Client using 443 port should have set confidentiality settings",
        tags = {"security"},
        priority = Priority.MINOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class HttpClientSSLShouldHaveConfidentiality extends AbstractResourceCheck {

    public static final String RULE_KEY = "HttpClientSSLShouldHaveConfidentiality";

    private static final Logger LOG = Loggers.get(HttpClientSSLShouldHaveConfidentiality.class);

    @Override
    public void validate(SharedResourceSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        SharedResource resource = resourceXml.getResource();
        LOG.debug("Checking project resource files");

        if (resource != null) {
            LOG.debug("Resource Type [" + resource.getType() + "]");
            if ("http:HttpClientConfiguration".equals(resource.getType())) {
                LOG.debug("Detected resource as HTTP Client. Continue to check details");
                SharedResourceParameter port = resource.getParameterByName("tcpDetails_port");
                if (port != null) {
                    LOG.debug("TCP Port: "+port);
                    if ("443".equals(port.getValue())) {
                        LOG.debug("Port detected as 443");
                        if (!(resource.getParameterByName("useSSL") != null || resource.getParameterByName("useDefaultSSL") != null)) {
                            reportIssueOnFile("Shared resource of type HTTP Client [" + resource.getName() + "] using 443 port is not setting Confidentiality settings");
                        }
                    }
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