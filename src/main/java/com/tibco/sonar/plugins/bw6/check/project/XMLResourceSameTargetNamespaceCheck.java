/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.check.project;

import com.tibco.sonar.plugins.bw6.check.AbstractProjectCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProjectSource;
import com.tibco.sonar.plugins.bw6.source.XsdMap;
import com.tibco.utils.bw6.model.Project;
import com.tibco.utils.bw6.model.WsdlResource;
import com.tibco.utils.bw6.model.XmlResource;
import com.tibco.utils.bw6.model.XsdResource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sonar.api.batch.fs.InputFile;

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(
        key = XMLResourceSameTargetNamespaceCheck.RULE_KEY,
        name = "XML Schema / WSDL file share same target namespace",
        description = "Check if most that one XML Schema or WSDL file have same target namespace",
        priority = Priority.MINOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class XMLResourceSameTargetNamespaceCheck extends AbstractProjectCheck {

    public static final String RULE_KEY = "XMLResourceSameTargetNamespace";

    private static final Logger LOG = Loggers.get(XMLResourceSameTargetNamespaceCheck.class);

    
    @Override
    public void validate(ProjectSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        Project project = resourceXml.getProject();
        XsdMap map = resourceXml.getMap();
        if(project != null){
        LOG.debug("Checking project files");
        
        List<XmlResource> resourceList = new ArrayList<>();
        List<XsdResource> schemaList = project.getSchemas();
        if(schemaList != null){
            schemaList.forEach((schema) -> {
                resourceList.add(schema);
            });
        }
        
        List<WsdlResource> descriptorList = project.getServiceDescriptor();
        if(descriptorList != null){
            descriptorList.forEach((schema) -> {
                resourceList.add(schema);
            });
        }
        
        if(resourceList != null){
            Map<String, List<InputFile>> targetNamespaceMap = new HashMap<>();
            resourceList.forEach((schema) -> {
                String targetNamespace = schema.getTargetNamespace();
                LOG.debug("Checking target namespace ["+targetNamespace+"]");
                List<InputFile> schemas = targetNamespaceMap.get(targetNamespace);
                if(schemas == null){
                    schemas = new ArrayList<>();
                }
                schemas.add(map.getFile(schema));
                targetNamespaceMap.put(targetNamespace, schemas);
                if (schemas.size() > 1) {
                    reportIssueOnFile("Target namespace ["+targetNamespace+"] is replicated among xsd resources project has", map.getFile(schema) , 1);
                }
            });
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
