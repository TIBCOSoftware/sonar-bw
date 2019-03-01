package com.tibco.sonar.plugins.bw6.profile;

import com.tibco.sonar.plugins.bw6.check.AbstractCheck;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import com.tibco.sonar.plugins.bw6.language.BWProcessLanguage;
import com.tibco.sonar.plugins.bw6.rulerepository.ProcessRuleDefinition;
import static com.tibco.sonar.plugins.bw6.rulerepository.ProcessRuleDefinition.REPOSITORY_KEY; 
import java.util.List;
/**
 * Default, BuiltIn Quality Profile for the projects having files of the language "BW"
 */
public final class BWProcessQualityProfile implements BuiltInQualityProfilesDefinition {

   public static final String PROFILE_NAME = "BW6 Quality Profile";
    
   @Override
    public void define(Context context) {
      NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile(PROFILE_NAME, BWProcessLanguage.KEY);
      profile.setDefault(true);
      
       List<AbstractCheck> checks = ProcessRuleDefinition.getCheckList();
       for(AbstractCheck check : checks){
           profile.activateRule(REPOSITORY_KEY, check.getRuleKeyName());
           
       }
      profile.done();
    }


}