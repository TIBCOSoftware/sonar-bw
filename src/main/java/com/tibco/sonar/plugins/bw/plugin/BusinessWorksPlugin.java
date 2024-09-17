/*
 * Copyright (C) 2016-2023. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw.plugin;

import com.tibco.sonar.plugins.bw5.settings.BW5LanguageFileSuffixProperty;
import com.tibco.sonar.plugins.bw5.language.BusinessWorks5Language;
import com.tibco.sonar.plugins.bw5.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw5.sensor.BWResourceMetricSensor;
import com.tibco.sonar.plugins.bw5.sensor.GlobalVariableMetricSensor;
import com.tibco.sonar.plugins.bw5.sensor.ProcessMetricSensor;
import org.sonar.api.Plugin;

import com.tibco.sonar.plugins.bw6.language.BWProcessLanguage;
import com.tibco.sonar.plugins.bw6.measurecomputer.resources.*;
import com.tibco.sonar.plugins.bw6.metric.SharedResourceMetrics;
import com.tibco.sonar.plugins.bw6.rulerepository.ProcessRuleDefinition;
//import com.tibco.sonar.plugins.bw6.sensor.ProcessRuleSensor;
import com.tibco.sonar.plugins.bw6.sensor.ProcessRuleSensor;
import com.tibco.sonar.plugins.bw6.settings.BW6LanguageFileSuffixProperty;

public class BusinessWorksPlugin implements Plugin {

	@Override
	public void define(Context context) {
	   
		// BW6 Language Classes
		context.addExtensions(
       			BW6LanguageFileSuffixProperty.getPropertyDefinition(),
		    	BWProcessLanguage.class,
	            ProcessRuleDefinition.class,
				ProcessRuleSensor.class,
				com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile.class);


		//BW5 Language Classes
		context.addExtensions(
				BW5LanguageFileSuffixProperty.getPropertyDefinition(),
				BusinessWorks5Language.class,
				com.tibco.sonar.plugins.bw5.rulerepository.ProcessRuleDefinition.class,
				com.tibco.sonar.plugins.bw5.sensor.ProcessRuleSensor.class,
				GlobalVariableMetricSensor.class,
				BWResourceMetricSensor.class,
				ProcessMetricSensor.class,
				BWProcessQualityProfile.class
		);

		// Resource File Metric Information
		context.addExtensions(
			SharedResourceMetrics.class,
			ComputeBusinessDataFormatResource.class,		
			ComputeProxyConfigResource.class,	
			ComputeTotalJDBCResource.class,
			ComputeFTLRealmConnectionResource.class,		
			ComputeTotalRVResource.class,	
			ComputeTotalHTTPClientResource.class,
			ComputeFTPConnectionResource.class,		
			ComputeTotalSMTPResource.class,			
			ComputeTCPConnectionResource.class,
			ComputeIdentityProviderResource.class,		
			ComputeTotalSSLClientConfigResource.class,	
			ComputeTotalXMLAuthResource.class,
			ComputeJavaGlobalInstanceResource.class,		
			ComputeTotalSSLServerConfigResource.class,	
			ComputeThreadPoolResource.class,
			ComputeJMSConfigResource.class,			
			ComputeTotalSubjectProviderResource.class,
			ComputeTotalHTTPConnectionResource.class,
			ComputeJNDIConfigResource.class,	
			ComputeTotalWSSAuthResource.class,		
			ComputeKeyStoreProviderResource.class,		
			ComputeTotalTrustProviderResource.class,
			ComputeLDAPAuthResource.class,	
			ComputeTotalSQLResource.class);		
	}

}



