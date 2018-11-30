package com.tibco.sonar.plugins.bw6.profile;

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import com.tibco.sonar.plugins.bw6.language.BWProcessLanguage;

//import static org.sonarsource.plugins.example.rules.FooLintRulesDefinition.REPO_KEY;
import static com.tibco.sonar.plugins.bw6.rulerepository.ProcessRuleDefinition.REPOSITORY_KEY; 
/**
 * Default, BuiltIn Quality Profile for the projects having files of the language "BW"
 */
public final class BWProcessQualityProfile implements BuiltInQualityProfilesDefinition {

   @Override
    public void define(Context context) {
      NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile("BW6 Quality Profile", BWProcessLanguage.KEY);
      profile.setDefault(true);
  
      NewBuiltInActiveRule rule1 = profile.activateRule(REPOSITORY_KEY, "CheckpointProcessHTTP");
      rule1.overrideSeverity("BLOCKER");
      NewBuiltInActiveRule rule2 = profile.activateRule(REPOSITORY_KEY, "CheckpointProcessJDBC");
      rule2.overrideSeverity("MAJOR");
      NewBuiltInActiveRule rule3 = profile.activateRule(REPOSITORY_KEY, "CheckpointProcessREST");
      rule3.overrideSeverity("CRITICAL");
  
      profile.done();
    }


}