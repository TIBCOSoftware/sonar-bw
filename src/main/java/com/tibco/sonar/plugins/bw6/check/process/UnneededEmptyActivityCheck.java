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

import com.thoughtworks.xstream.persistence.XmlArrayList;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw6.constants.BwpModelConstants;
import com.tibco.utils.bw6.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Group;
import com.tibco.utils.bw6.model.Process;
import java.util.List;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.w3c.dom.Element;

@Rule(key = UnneededEmptyActivityCheck.RULE_KEY, name = "Unneeded Empty Activity", priority = Priority.MINOR, description = "This rule checks for empty activities that only have one input transition and output tranistion so they are not providing any value")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class UnneededEmptyActivityCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(UnneededEmptyActivityCheck.class);
    public static final String RULE_KEY = "UnneededEmptyActivity";
    
    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();            
        if(process != null){
            for(Activity activity : process.getActivitiesByType(BwpModelConstants.BPWSEMPTY)){
                LOG.debug("Activty type ["+activity.getType() + "] - ["+activity.getName()+"]");
                
                if(activity.getInputTransitions().size() == 1 && activity.getOutputTransitions().size() == 1){
                    if(!"ERROR".equals(activity.getInputTransitions().get(0).getConditionType())){
                        reportIssueOnFile("Empty activity ["+activity.getName()+"] should be avoided as it is not helping to make understandable the flow",XmlHelper.getLineNumber(activity.getNode()));
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
}
