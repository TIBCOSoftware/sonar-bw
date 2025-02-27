/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.project;

import com.tibco.sonar.plugins.bw6.check.AbstractProjectCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProjectSource;
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.bw6.model.Project;

import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Rule(
        key = PomXmlVersionsHarcodedCheck.RULE_KEY,
        name = "Pom.xml dependency versions are hardcoded",
        description = "Check if the dependency version are being defined using a property or hardcoded",
        priority = Priority.MINOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class PomXmlVersionsHarcodedCheck extends AbstractProjectCheck {

    public static final String RULE_KEY = "PomXmlVersionsHarcoded";

    private static final Logger LOG = LoggerFactory.getLogger(PomXmlVersionsHarcodedCheck.class);

    
    
    @Override
    public void validate(ProjectSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        Project project = resourceXml.getProject();
        LOG.debug("Checking project files");
        
        if(project != null && project.getPomFile() != null) {
            Document pom = project.getPomFile();
            NodeList dependency = XmlHelper.evaluateXPath(pom.getDocumentElement(), "//dependency");
            if(dependency != null){
                for(int i=0;i<dependency.getLength();i++){
                    checkDependency(dependency, i);
                }
            }

        }
        
    }

    private void checkDependency(NodeList dependency, int i) {
        Node dep = dependency.item(i);
        Element artifactIdEl = XmlHelper.firstChildElement(dep, "artifactId");
        String artifactId = artifactIdEl == null ? "" : artifactIdEl.getNodeValue();

        Element versionEl = XmlHelper.firstChildElement(dep, "version");
        String version = versionEl == null ? "" : versionEl.getNodeValue();

        if(!version.startsWith("${")){
            reportIssueOnFile("pom.xml version from artifactId ["+artifactId+"] is harcoded: ["+version+"]");
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
