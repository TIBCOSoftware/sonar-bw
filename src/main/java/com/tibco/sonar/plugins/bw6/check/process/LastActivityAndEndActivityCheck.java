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
package com.tibco.sonar.plugins.bw6.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import static com.tibco.utils.bw6.constants.BwpModelConstants.BPWSEXIT;
import static com.tibco.utils.bw6.constants.BwpModelConstants.BPWSREPLY;
import static com.tibco.utils.bw6.constants.BwpModelConstants.BPWSRETHROW;
import static com.tibco.utils.bw6.constants.BwpModelConstants.BPWSTHROW;
import com.tibco.utils.bw6.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Process;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = LastActivityAndEndActivityCheck.RULE_KEY, name = "Last activity Is An end activity", priority = Priority.INFO, description = "This rule checks all flows are finished properly using an end activity")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.INFO)
public class LastActivityAndEndActivityCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(LastActivityAndEndActivityCheck.class);
    public static final String RULE_KEY = "LastActivityAndEndActivity";
    
    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();            
        if(process != null){
  
            
            for(Activity activity : process.getActivities()){
                if(activity.getOutputTransitions().isEmpty() && !process.belongActivityToGroup(activity)){
                    if(activity.getType() == null || !isActivityEnd(activity.getType())){
                        reportIssueOnFile("End activity ["+activity.getName()+"] shouldn't be the last activity of the flow, as this should end with an End activity",XmlHelper.getLineNumber(activity.getNode()));                      
                    }
                }
            }
           
        }
        LOG.debug("Validation ended for rule: " + RULE_KEY);
    }

    @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }

    @Override
    public Logger getLogger() {
        return LOG;
    }

    private boolean isActivityEnd(String type) {
        return type != null && (type.equals(BPWSRETHROW) || type.equals("bw.internal.end") || type.equals(BPWSREPLY) || type.equals(BPWSEXIT) || type.equals(BPWSTHROW));
    }
}
