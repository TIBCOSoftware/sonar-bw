/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
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

@Rule(
        key = AtLeastOneStarterCheck.RULE_KEY,
        name = "Check number of starters in an Application Module",
        description = "Check that an application module should have one starter additional to the Activator process",
        priority = Priority.MINOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class AtLeastOneStarterCheck extends AbstractProjectCheck {

    public static final String RULE_KEY = "AtLeastOneStarter";

    private static final Logger LOG = Loggers.get(AtLeastOneStarterCheck.class);

    
    
    @Override
    public void validate(ProjectSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        Project project = resourceXml.getProject();
        LOG.debug("Checking project files");
        
        if(project != null && project.isIsApplicationModule()){
            LOG.debug("Application Module detected");
            int sizeOfComponents = project.getComponents().size();
            if(project.hasActivator()){
                sizeOfComponents--;
            }
            
            if(sizeOfComponents == 0){
                reportIssueOnFile("Application Module doesn't have an additional Starter to the Activator");
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
