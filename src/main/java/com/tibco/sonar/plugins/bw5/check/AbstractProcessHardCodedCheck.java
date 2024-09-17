/*
 * SonarQube XML Plugin
 * Copyright (C) 2010 SonarSource
 * dev@sonar.codehaus.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tibco.sonar.plugins.bw5.check;

import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import com.tibco.utils.common.helper.XmlHelper;
import org.w3c.dom.Node;

import com.tibco.sonar.plugins.bw5.source.ProcessSource;

import com.tibco.utils.bw5.model.Activity;
import com.tibco.utils.bw5.model.Process;


/**
 * Abtract superclass for hard coded values checks on specific Process
 * activities.
 *
 * @author Gilles Seghaier
 */
public abstract class AbstractProcessHardCodedCheck extends
        AbstractProcessCheck {

    public abstract String getConfigXPath();

    public abstract void setConfigXPath(String configXPath);

    public abstract String getInputBindingXPath();

    public abstract void setInputBindingXPath(String inputBindingXPath);

    public abstract String getActivityType();

    public abstract void setActivityType(String activityType);

    public abstract String getMessage();

    public abstract void setMessage(String message);

    protected void validate(ProcessSource processSource) {
        Process process = processSource.getProcessModel();
        // Get all activities that match to the activity type
        List<Activity> activities = process.getActivitiesByType(getActivityType());
        // Add starter if match with the activity type
        if (process.getStarter().getType().equals(getActivityType())) {
            activities.add(process.getStarter());
        }
        // Add ender if match with the activity type
        if (process.getEnder().getType().equals(getActivityType())) {
            activities.add(process.getEnder());
        }
        
        // For each activity found
        for (Activity activity : activities) {
            // By default prepare to check activity configuration
            Boolean checkConfig = true;
            // If input binding xpath is defined in order
            // to retrieve a configuration node to check	
            if (getInputBindingXPath() != null && !getInputBindingXPath().isEmpty()) {
                // Retrieve target node
                Node targetedMappedConfig;
                try {
                    targetedMappedConfig = XmlHelper.evaluateXpathNode(activity.getInputBindings(), getInputBindingXPath());
                    if (targetedMappedConfig != null) {
                        reportIssueOnFile(getMessage());
                        checkConfig = false;
                    } else {
                        checkConfig = true;
                    }
                } catch (Exception e) {
                    // Cannot evaluate XPath query to retrieve the mapping 
                    // element then check directly in the configuration
                    checkConfig = true;
                }
            }
            // if check config is possible
            if (checkConfig && getConfigXPath() != null && !getConfigXPath().isEmpty()) {                
                reportIssueOnFile(getMessage());
            }
        }        
    }
    
}
