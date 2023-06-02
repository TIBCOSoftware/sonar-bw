/*
 * Copyright (C) 2016-2023. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
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
        key = HttpConnectorShouldHaveConfidentiality.RULE_KEY,
        name = "HTTP Connector should have confidentiality",
        description = "HTTP Connector should have set security confidentiality or jwt authentication settings",
        tags = {"security"},
        priority = Priority.MINOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class HttpConnectorShouldHaveConfidentiality extends AbstractResourceCheck {

    public static final String RULE_KEY = "HttpConnectorShouldHaveConfidentiality";
    private static final Logger LOG = Loggers.get(HttpConnectorShouldHaveConfidentiality.class);

    @Override
    public void validate(SharedResourceSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        SharedResource resource = resourceXml.getResource();
        LOG.debug("Checking project resource files");

        if (resource != null) {
            LOG.debug("Resource Type [" + resource.getType() + "]");
            if ("httpconnector:HttpConnectorConfiguration".equals(resource.getType())) {
                LOG.debug("Detected resource as HTTP Connector. Continue to check details");
                if (!(resource.getParameterByName("useSSL") != null || resource.getParameterByName("useJWT") != null)) {
                    reportIssueOnFile("Shared resource of type HTTP Connector [" + resource.getName() + "] is not using security confidentiality nor jwt authentication settings");

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

