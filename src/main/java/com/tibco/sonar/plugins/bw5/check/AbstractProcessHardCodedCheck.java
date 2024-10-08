/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.check;

import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import com.tibco.utils.bw5.helper.GvHelper;
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
            checkActivity(activity);
        }        
    }

    private void checkActivity(Activity activity) {
        // By default prepare to check activity configuration
        boolean checkConfig = true;
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
            Node targetedMappedConfig = XmlHelper.evaluateXpathNode(activity.getConfiguration(), getConfigXPath());
            if(targetedMappedConfig != null && !GvHelper.isGVReference(targetedMappedConfig.getTextContent())){
                reportIssueOnFile(getMessage());
            }


        }
    }

}
