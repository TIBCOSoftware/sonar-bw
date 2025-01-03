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
import com.tibco.utils.bw6.model.SharedResourceParameter;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import java.util.Objects;

@Rule(
        key = SharedResourceUsingModuleProperty.RULE_KEY,
        name = "Parameter Resource using Module Property",
        description = "Parameter Resource using Module Property",
        priority = Priority.MINOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)

public class SharedResourceUsingModuleProperty extends AbstractResourceCheck {

    public static final String RULE_KEY = "BwSharedResourceUsingModuleProperty";
    private static final Logger LOG = LoggerFactory.getLogger(SharedResourceUsingModuleProperty.class);

    @Override
    public void validate(SharedResourceSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        SharedResource resource = resourceXml.getResource();
        LOG.debug("Checking project resource files");
        for (SharedResourceParameter prop : resource.getProperties()) {
            LOG.debug("Parameter name : [" + prop.getName() + "] value: [" + prop.getValue() + "]" + " Global Variable [" + prop.isGlobalVariable() + "] isBinding [" + prop.isBinding() + "] isRequired [" + prop.isRequired() + "]");
        }

        resource.getProperties().stream().filter(Objects::nonNull).filter(parameter -> (parameter.isRequired() && !parameter.isGlobalVariable())).forEachOrdered(parameter ->  reportIssueOnFile(String.format("Parameter [ %s ] should be defined using module property and it is not",parameter.getName())));

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
