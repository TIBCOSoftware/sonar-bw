/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.check.activity.catcherror;

import org.sonar.check.Cardinality;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;

import com.tibco.sonar.plugins.bw5.check.AbstractProcessCatchCheck;
import com.tibco.sonar.plugins.bw5.check.CheckConstants;
import com.tibco.utils.bw5.model.Constants;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = CustomCatchCheck.RULE_KEY, name = CheckConstants.RULE_PROCESS_CUSTOMPROCESSCATCH_NAME , description = CheckConstants.RULE_PROCESS_CUSTOMPROCESSCATCH_DESCRIPTION, priority = Priority.MAJOR)
public class CustomCatchCheck extends AbstractProcessCatchCheck {

    private static final Logger LOG = Loggers.get(CatchAllCheck.class);
	public static final String RULE_KEY = "CustomProcessCatch";
	
	public static final String CATCH_ALL_CONFIG_ELEMENT = "catchAll";
	public static final String CATCH_ALL_CONFIG_VALUE = "true";
	public static final String CATCH_BWE_CONFIG_ELEMENT = "fault";
	public static final String CATCH_BWE_CONFIG_VALUE = "localname=myOwnException namespace=http://www.tibco.com/MyOwnException";
	public static final String NOT_FOUND_MESSAGE = "No catch activity found based on the rule conditions";
	public static final String NO_CATCH_MESSAGE = "None catch activity exists";

	public static final String CATCH_ACTIVITY_TYPE = Constants.ACTIVITY_TYPES.CORE_CATCH.getName();
	
	@RuleProperty(key = "catchActivityType", type = "TEXT", defaultValue="")
	private String catchActivityType = "";
	
	@RuleProperty(key = "catchAllElementName", type = "TEXT", defaultValue=CATCH_ALL_CONFIG_ELEMENT)
	private String catchAllElementName = CATCH_ALL_CONFIG_ELEMENT;
	
	@RuleProperty(key = "catchAllElementValue", type = "TEXT", defaultValue=CATCH_ALL_CONFIG_VALUE)
	private String catchAllElementValue = CATCH_ALL_CONFIG_VALUE;

	@RuleProperty(key = "catchFaultElementName", type = "TEXT", defaultValue=CATCH_BWE_CONFIG_ELEMENT)
	private String catchFaultElementName = CATCH_BWE_CONFIG_ELEMENT;

	@RuleProperty(key = "catchFaultElementValue", type = "TEXT", defaultValue=CATCH_BWE_CONFIG_VALUE)
	private String catchFaultElementValue = CATCH_BWE_CONFIG_VALUE;

	@RuleProperty(key = "notFoundMessage", type = "TEXT", defaultValue=NOT_FOUND_MESSAGE)
	private String notFoundMessage = NOT_FOUND_MESSAGE;

	@RuleProperty(key = "noCatchMessage", type = "TEXT", defaultValue=NO_CATCH_MESSAGE)
	private String noCatchMessage = NO_CATCH_MESSAGE;
	
	@Override
	public String getCatchFaultElementValue() {
		return catchFaultElementValue;
	}

	@Override
	public void setCatchFaultElementValue(String catchFaultElementValue) {
		this.catchFaultElementValue = catchFaultElementValue;
	}

	@Override
	public String getCatchFaultElementName() {
		return catchFaultElementName;
	}

	@Override
	public void setCatchFaultElementName(String catchFaultElementName) {
		this.catchFaultElementName = catchFaultElementName;
	}

	@Override
	public String getCatchAllElementValue() {
		return catchAllElementValue;
	}
	
	@Override
	public void setCatchAllElementValue(String catchAllElementValue) {
		this.catchAllElementValue = catchAllElementValue;
	}

	@Override
	public String getCatchAllElementName() {
		return catchAllElementName;
	}
	
	@Override
	public void setCatchAllElementName(String catchAllElementName) {
		this.catchAllElementName = catchAllElementName;
	}

	@Override
	public String getCatchActivityType() {
		if(catchActivityType.isEmpty()){
			return Constants.ACTIVITY_TYPES.CORE_CATCH.getName();
		}
		return catchActivityType;
	}
	
	@Override
	public void setCatchActivityType(String catchActivityType) {
		this.catchActivityType = catchActivityType;
	}

	@Override
	public String getNotFoundMessage() {
		return notFoundMessage;
	}
	
	@Override
	public void setNotFoundMessage(String notFoundMessage) {
		this.notFoundMessage = notFoundMessage;
	}

	@Override
	public String getNoCatchMessage() {
		return noCatchMessage;
	}
	
	@Override
	public void setNoCatchMessage(String noCatchMessage) {
		this.noCatchMessage = noCatchMessage;
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
