/*
 * SonarQube Java
 * Copyright (C) 2012 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package com.tibco.sonar.plugins.bw5.check.activity.catcherror;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;

import com.tibco.sonar.plugins.bw5.check.AbstractProcessCatchCheck;
import com.tibco.sonar.plugins.bw5.check.CheckConstants;
import com.tibco.sonar.plugins.bw5.profile.BWProcessQualityProfile;
import com.tibco.utils.bw5.model.Constants;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = CatchBWExceptionCheck.RULE_KEY, name = CheckConstants.RULE_PROCESS_PROCESSCATCHBWEXCEPTION_NAME, description = CheckConstants.RULE_PROCESS_PROCESSCATCHBWEXCEPTION_DESCRIPTION , priority = Priority.MAJOR)
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class CatchBWExceptionCheck extends AbstractProcessCatchCheck {

    private static final Logger LOG = Loggers.get(CatchAllCheck.class);
	public static final String RULE_KEY = "ProcessCatchBWException";
	public static final String CATCH_ACTIVITY_TYPE = Constants.ACTIVITY_TYPES.CORE_CATCH.getName();
	public static final String CATCH_ALL_CONFIG_ELEMENT = "catchAll";
	public static final String CATCH_ALL_CONFIG_VALUE = "true";
	public static final String CATCH_BWE_CONFIG_ELEMENT = "fault";
	public static final String CATCH_BWE_CONFIG_VALUE = "localname=bwException namespace=http://soa.coe.com/CommonTypes/Mediation/Framework/BWException";
	public static final String NOT_FOUND_MESSAGE = "No catch BWException activity found";
	public static final String NO_CATCH_MESSAGE = "None catch activity exists";
		
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
