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

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(
        key = EndpointURIFromHTTPBindingSetUsingPropertyCheck.RULE_KEY,
        name = "Endpoint URI from SOAP/HTTP Binding Set using property",
        description = "Endpoint URI from SOAP/HTTP Binding should be set using a Module Property",
        priority = Priority.MINOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class EndpointURIFromHTTPBindingSetUsingPropertyCheck extends AbstractProjectCheck {

    public static final String RULE_KEY = "EndpointURIFromHTTPBindingSetUsingProperty";

    private static final Logger LOG = Loggers.get(EndpointURIFromHTTPBindingSetUsingPropertyCheck.class);

   
    @Override
    public void validate(ProjectSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        Project project = resourceXml.getProject();
        LOG.debug("Checking project files");
        if(project != null && project.getComponents() != null){
            LOG.debug("Properties are not null");
            for(int i=0;i<project.getComponents().size();i++){
                Component comp  = project.getComponents().get(i);
                if(comp != null){
                    LOG.debug("Component is not null");
                    if(comp.getServices() != null){
                    for(int j=0; j<comp.getServices().size();j++){
                        Service service = comp.getServices().get(j);
                        if(service != null){
                            LOG.debug("Component is not null");
                            Binding binding = service.getBinding();
                            if(binding.getUri() != null && !"".equals(binding.getUri()) && !binding.isIsPropertyURI()){
                                reportIssueOnFile("Endpoint URI from binding in component ["+comp.getName()+"] should be set using a Module Property");
                            }
                        }
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
