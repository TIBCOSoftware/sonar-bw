/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.project;

import com.tibco.sonar.plugins.bw6.check.AbstractProjectCheck;
import com.tibco.sonar.plugins.bw6.metric.SharedResourceMetrics;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProjectSource;
import com.tibco.utils.bw6.model.Project;
import com.tibco.utils.bw6.model.SharedResource;
import java.util.ArrayList;
import java.util.List;

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(
        key = OnlyOneKeystoreApplicationModuleCheck.RULE_KEY,
        name = "Check number of starters in an Application Module",
        description = "Check that an application module should have one starter additional to the Activator process",
        priority = Priority.MINOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class OnlyOneKeystoreApplicationModuleCheck extends AbstractProjectCheck {

    public static final String RULE_KEY = "OnlyOneKeystoreApplicationModule";

    private static final Logger LOG = Loggers.get(OnlyOneKeystoreApplicationModuleCheck.class);

    @Override
    public void validate(ProjectSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        Project project = resourceXml.getProject();
        LOG.debug("Checking project files");

        if (project != null && project.isIsApplicationModule()) {
            LOG.debug("Application Module detected");
            List<String> keystoreNames = new ArrayList<>();
            for (SharedResource resource : project.getResources()) {
                if (SharedResourceMetrics.BWRESOURCES_KEYSTORE_PROVIDER_KEY.equals(resource.getType())) {
                    keystoreNames.add(resource.getName());
                }

            }

            if (!keystoreNames.isEmpty()) {
                String keyStoreNames = "";
                for (String kName : keystoreNames) {
                    keyStoreNames += " " + kName;
                }
                reportIssueOnFile("Application Module has more than one keystore inside [" + keyStoreNames + "]");
            }
        }

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
