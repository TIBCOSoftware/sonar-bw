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
        key = SharedResourceUsingModuleProperty.RULE_KEY,
        name =  "Parameter Resource using Module Property",
        description = "Parameter Resource using Module Property",
        priority = Priority.MINOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)

public class SharedResourceUsingModuleProperty extends AbstractResourceCheck{

    public static final String RULE_KEY = "BwSharedResourceUsingModuleProperty";
    private static final Logger LOG = Loggers.get(SharedResourceUsingModuleProperty.class);

    @Override
     public void validate(SharedResourceSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
            SharedResource resource =  resourceXml.getResource();
            LOG.debug("Checking project files");
            resource.getProperties().stream().filter((parameter) -> (parameter != null)).filter((parameter) -> (parameter.isBinding() && !parameter.isGlobalVariable())).forEachOrdered((parameter) -> {
                reportIssueOnFile(String.format("Parameter ["+parameter.getName()+"] should be defined using module property and it is not"));
        });
        
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
