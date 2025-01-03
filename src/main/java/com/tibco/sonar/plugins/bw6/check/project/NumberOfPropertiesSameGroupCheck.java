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
import com.tibco.utils.bw6.model.Property;
import java.util.List;

import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;

@Rule(
        key = NumberOfPropertiesSameGroupCheck.RULE_KEY,
        name = "Number Of Module Properties in same group",
        description = "Check the maximum of module properties that you should have together in the same group to ensure a proper and maintable property organization",
        priority = Priority.MINOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class NumberOfPropertiesSameGroupCheck extends AbstractProjectCheck {

    public static final String RULE_KEY = "NumberOfPropertiesSameGroup";

    private static final Logger LOG = LoggerFactory.getLogger(NumberOfPropertiesSameGroupCheck.class);

    @RuleProperty(key = "maxProperties", description = "Threshold of properties to be considered excessive for a single group", defaultValue = "10", type = "INTEGER")
    protected int maxProperties;

    
    @Override
    public void validate(ProjectSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        Project project = resourceXml.getProject();
        LOG.debug("Checking project files");
        if(project != null && project.getProperties() != null){
            LOG.debug("Properties are not null");
            List<String> propertyPathList = project.getProperties().getPropertyPaths();
            if(propertyPathList != null){
                LOG.debug("Properties path list: "+propertyPathList.size());
                for(String propertyPath : propertyPathList){
                    checkProperties(propertyPath, project);

                    LOG.debug("Checking Path: "+propertyPath + " DONE");
                }
            }
        }
        
    }

    private void checkProperties(String propertyPath, Project project) {
        LOG.debug("Checking Path: "+ propertyPath);
        List<Property> propertyList = project.getProperties().getPropertyByPath(propertyPath);
        if(propertyList != null){
            LOG.debug("Number of properties that belong to path: "+ propertyPath + " - " + propertyList.size()) ;
            if(propertyList.size() > maxProperties){
                reportIssueOnFile("Module Property group ["+ propertyPath +"] has more properties  ("+propertyList.size()+") that allowed ("+maxProperties+") ");
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
