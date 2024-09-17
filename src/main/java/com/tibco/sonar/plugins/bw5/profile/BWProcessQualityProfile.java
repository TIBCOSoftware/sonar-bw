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
