/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.check.activity;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import com.tibco.sonar.plugins.bw5.check.AbstractProcessHardCodedCheck;
import com.tibco.sonar.plugins.bw5.check.CheckConstants;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = CustomHardCodedCheck.RULE_KEY, name = CheckConstants.RULE_PROCESS_CUSTOMHARDCODEDACTIVITY_NAME, description = CheckConstants.RULE_PROCESS_CUSTOMHARDCODEDACTIVITY_DESCRIPTION, priority = Priority.MAJOR)
public class CustomHardCodedCheck extends AbstractProcessHardCodedCheck {

    private static final Logger LOG = Loggers.get(CustomHardCodedCheck.class);
    public static final String RULE_KEY = "CustomHardCodedActivity";

    @RuleProperty(key = "configXPath", type = "TEXT")
    private String configXPath;

    @RuleProperty(key = "inputBindingXPath", type = "TEXT")
    private String inputBindingXPath;

    @RuleProperty(key = "activityType")
    private String activityType;

    @RuleProperty(key = "message")
    private String message;

    public String getConfigXPath() {
        return configXPath;
    }

    public void setConfigXPath(String configXPath) {
        this.configXPath = configXPath;
    }

    public String getInputBindingXPath() {
        return inputBindingXPath;
    }

    public void setInputBindingXPath(String inputBindingXPath) {
        this.inputBindingXPath = inputBindingXPath;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
