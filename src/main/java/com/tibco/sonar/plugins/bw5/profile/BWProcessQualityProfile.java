/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.profile;

import com.tibco.sonar.plugins.bw.check.AbstractCheck;
import com.tibco.sonar.plugins.bw5.check.XPathCheck;
import com.tibco.sonar.plugins.bw5.language.BusinessWorks5Language;
import com.tibco.sonar.plugins.bw5.rulerepository.ProcessRuleDefinition;
import static com.tibco.sonar.plugins.bw5.rulerepository.ProcessRuleDefinition.REPOSITORY_KEY;
import java.util.List;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;

public class BWProcessQualityProfile implements BuiltInQualityProfilesDefinition {

	public static final String PROFILE_NAME = "BW5 Quality Profile";
    
   @Override
    public void define(BuiltInQualityProfilesDefinition.Context context) {
      BuiltInQualityProfilesDefinition.NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile(PROFILE_NAME, BusinessWorks5Language.KEY);
      profile.setDefault(true);
      
       List<AbstractCheck> checks = ProcessRuleDefinition.getCheckList();
       for(AbstractCheck check : checks){
           if(! (check instanceof XPathCheck)){
           profile.activateRule(REPOSITORY_KEY, check.getRuleKeyName());
           }
           
       }
      profile.done();
    }
}
