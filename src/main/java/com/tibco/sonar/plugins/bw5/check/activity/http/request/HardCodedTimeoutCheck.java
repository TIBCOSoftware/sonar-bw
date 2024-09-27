/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.check.activity.http.request;

import org.sonar.check.Cardinality;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;

import com.tibco.sonar.plugins.bw5.check.AbstractProcessHardCodedCheck;
import com.tibco.sonar.plugins.bw5.check.CheckConstants;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = HardCodedTimeoutCheck.RULE_KEY, name = CheckConstants.RULE_PROCESS_HTTPREQUESTTIMEOUTHARDCODED_NAME, description = CheckConstants.RULE_PROCESS_HTTPREQUESTTIMEOUTHARDCODED_DESCRIPTION, priority = Priority.MAJOR,tags={"bug"}, cardinality = Cardinality.SINGLE)
public class HardCodedTimeoutCheck extends AbstractProcessHardCodedCheck {

    private static final Logger LOG = Loggers.get(HardCodedTimeoutCheck.class);
	public static final String RULE_KEY = "HTTPRequestTimeoutHardCoded";
	
	protected static final String CONFIG_XPATH_DEFAULT = "";
	protected static final String INPUTBINDING_XPATH_DEFAULT = "//RequestActivityInput/Timeout";
	protected static final String ACTIVITY_TYPE_DEFAULT = "com.tibco.plugin.http.client.HttpRequestActivity";
	protected static final String MESSAGE_DEFAULT = "Timeout hard coded in HTTP Request Reply activity";
	
	@RuleProperty(key = "configXPath", type = "TEXT", defaultValue=CONFIG_XPATH_DEFAULT)
	private String configXPath = CONFIG_XPATH_DEFAULT;
	
	@RuleProperty(key = "inputBindingXPath", type = "TEXT", defaultValue=INPUTBINDING_XPATH_DEFAULT)
	private String inputBindingXPath = INPUTBINDING_XPATH_DEFAULT;

	@RuleProperty(key = "activityType", defaultValue=ACTIVITY_TYPE_DEFAULT)
	private String activityType = ACTIVITY_TYPE_DEFAULT;

	@RuleProperty(key = "message", type="TEXT", defaultValue=MESSAGE_DEFAULT)
	private String message = MESSAGE_DEFAULT;
	
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
