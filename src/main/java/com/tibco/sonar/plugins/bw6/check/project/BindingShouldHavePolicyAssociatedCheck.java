/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.project;

import com.tibco.sonar.plugins.bw6.check.AbstractProjectCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProjectSource;
import com.tibco.utils.bw6.model.Binding;
import com.tibco.utils.bw6.model.Component;
import com.tibco.utils.bw6.model.Project;
import com.tibco.utils.bw6.model.Service;

import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(
        key = BindingShouldHavePolicyAssociatedCheck.RULE_KEY,
        name = "HTTP Binding should have a policy associated",
        tags = {"security"},
        description = "To ensure that the communications are authentified all input connections should check that the binding has a policy associated",
        priority = Priority.MAJOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class BindingShouldHavePolicyAssociatedCheck extends AbstractProjectCheck {

    public static final String RULE_KEY = "BindingShouldHavePolicyAssociated";

    private static final Logger LOG = LoggerFactory.getLogger(BindingShouldHavePolicyAssociatedCheck.class);

   
    @Override
    public void validate(ProjectSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        Project project = resourceXml.getProject();
        LOG.debug("Checking project files");
        if(project != null && project.getComponents() != null){
            LOG.debug("Properties are not null");
            for(Component comp : project.getComponents()){
                if(comp.getServices() != null){
                    LOG.debug("Services are not null");
                for(Service service : comp.getServices()){
                    checkService(comp, service);
                }
                }
            }
        }
            
        
    }

    private void checkService(Component comp, Service service) {
        if(service != null){
            LOG.debug("Service is not null");
            Binding binding = service.getBinding();
            if(binding != null){
                LOG.debug("Binding is not null");
                if("HTTP".equals(binding.getTransportBindingType()) && binding.getProperty("policySets") == null){
                    reportIssueOnFile("HTTP Binding of this component ["+ comp.getName()+"] should have a policy associated");
                }
            }
        }
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
