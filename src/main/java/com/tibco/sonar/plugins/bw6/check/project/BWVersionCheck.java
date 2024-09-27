/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.project;

import com.tibco.sonar.plugins.bw6.check.AbstractProjectCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProjectSource;
import com.tibco.utils.bw6.model.Project;

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;

import java.util.jar.Attributes;


@Rule(
        key = BWVersionCheck.RULE_KEY,
        name = "BWVersionCheck",
        description = "Check if this BusinessWorks Module matches recommended version which has better performance and less vulnerabilities",
        tags = {"security"},
        priority = Priority.MINOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)

public class BWVersionCheck extends AbstractProjectCheck {

    public static final String RULE_KEY = "BWVersionCheck";
    private static final Logger LOG = Loggers.get(BWVersionCheck.class);

    @RuleProperty(key = "baseline_bwversionpattern", description = "Regular expression for recommended version of BusinessWorks module. BW6 project should be created with version 6.5.x or above. TCI or BWCE project should be created with 2.5.x or above", defaultValue = "^(6.[5-9].*)|(2.[5-9].*)$", type = "TEXT")
    protected String baselineBWversionpattern;


    @Override
    public void validate(ProjectSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        Project project = resourceXml.getProject();
        LOG.debug("checking project files");
        if(project != null) {
            if(project.getManifest() == null){
                reportIssueOnFile("BusinessWorks module does not contain a Manifest");
            }
            else {
                Attributes attr = project.getManifest().getMainAttributes();

                if (attr != null) {
                    String appModule = attr.getValue("TIBCO-BW-Version");
                    if (appModule != null) {
                    int vend = appModule.indexOf('V');
                    if (vend != -1) { appModule = appModule.substring(0,vend).trim(); }
                    if (!appModule.matches(baselineBWversionpattern)) {
                            LOG.debug("Current TIBCO-BW-Version : " + appModule);
                            reportIssueOnFile("BusinessWorks module is not built with the recommended version");
                        }
                    }
                }
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
