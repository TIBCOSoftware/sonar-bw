package com.tibco.sonar.plugins.bw6.plugin;

import org.sonar.api.Plugin;
import com.tibco.sonar.plugins.bw6.language.BWProcessLanguage;
import com.tibco.sonar.plugins.bw6.rulerepository.ProcessRuleDefinition;
import com.tibco.sonar.plugins.bw6.sensor.ProcessRuleSensor;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.settings.BW6LanguageFileSuffixProperty;




public class BusinessWorksPlugin implements Plugin {
	
//   TODO: REMOVE.  THESE VALUES DON"T BELONG HERE AND HAVE BEEN MOVED.  JUST FOR REF AT MO.  REMOVE IN LATER VERSIONS.
//	 public static final String FILE_SUFFIXES_KEY = "sonar.bw.process.file.suffixes";
//	 public static final String TIBCO_BUSINESSWORK_CATEGORY = "TIBCO BusinessWorks 6";
//	 public static final String TIBCO_BUSINESSWORK_RULEREPOSITORY = "bwcommon";
//	 public static final String PROCESS_FILE_SUFFIXES_KEY = "sonar.bw.process.file.suffixes";

	  @Override
	  public void define(Context context) {
	    context.addExtensions(
       			BW6LanguageFileSuffixProperty.class,
		    	BWProcessLanguage.class,
	            ProcessRuleDefinition.class,
				ProcessRuleSensor.class,
				BWProcessQualityProfile.class
			);	
			}
		}



