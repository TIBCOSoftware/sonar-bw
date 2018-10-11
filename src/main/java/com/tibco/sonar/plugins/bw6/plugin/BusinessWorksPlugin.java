package com.tibco.sonar.plugins.bw6.plugin;

import com.tibco.sonar.plugins.bw6.profile.CommonRulesSonarWayProfile;

import org.sonar.api.Plugin;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;
import com.tibco.sonar.plugins.bw6.language.BWProcessLanguage;
import com.tibco.sonar.plugins.bw6.rulerepository.ProcessRuleDefinition;
import com.tibco.sonar.plugins.bw6.sensor.ProcessRuleSensor;

public class BusinessWorksPlugin implements Plugin {
	

	 public static final String FILE_SUFFIXES_KEY = "sonar.bw.process.file.suffixes";
	 public static final String TIBCO_BUSINESSWORK_CATEGORY = "TIBCO BusinessWorks 6";
	 public static final String TIBCO_BUSINESSWORK_RULEREPOSITORY = "bwcommon";
	 public static final String PROCESS_FILE_SUFFIXES_KEY = "sonar.bw.process.file.suffixes";

	  @Override
	  public void define(Context context) {
	    context.addExtensions(
	      PropertyDefinition.builder(BusinessWorksPlugin.FILE_SUFFIXES_KEY)
	        .name("File suffixes")
	        .description("Comma-separated list of suffixes for files to analyze.")
	        .defaultValue(".bwp")
	        .category("BWP")
	        .onQualifiers(Qualifiers.PROJECT)
	        .build(),
	      BWProcessLanguage.class,
	      ProcessRuleDefinition.class,
	      CommonRulesSonarWayProfile.class,
	      ProcessRuleSensor.class);
	  }
}
