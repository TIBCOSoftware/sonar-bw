package com.tibco.sonar.plugins.bw6.language;

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;

/**
 * Default, BuiltIn Quality Profile for the projects having files of the language "BW"
 */
public final class BWProcessQualityProfile implements BuiltInQualityProfilesDefinition {

   protected static final String REPO_KEY = "BW6";

   @Override
    public void define(Context context) {
      NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile("BW6 Rules", BWProcessLanguage.KEY);
      profile.setDefault(true);
  
      NewBuiltInActiveRule rule1 = profile.activateRule(REPO_KEY, "ExampleRule1");
      rule1.overrideSeverity("BLOCKER");
      NewBuiltInActiveRule rule2 = profile.activateRule(REPO_KEY, "ExampleRule2");
      rule2.overrideSeverity("MAJOR");
      NewBuiltInActiveRule rule3 = profile.activateRule(REPO_KEY, "ExampleRule3");
      rule3.overrideSeverity("CRITICAL");
  
      profile.done();
    }


}