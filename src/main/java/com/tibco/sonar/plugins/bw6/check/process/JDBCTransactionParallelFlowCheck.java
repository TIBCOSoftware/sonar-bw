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
import com.tibco.utils.bw6.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Group;
import com.tibco.utils.bw6.model.Process;
import java.util.List;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = JDBCTransactionParallelFlowCheck.RULE_KEY, name = "JDBC Transaction Parallel Flow ", priority = Priority.MAJOR, description = "This rule checks if there is no parallel flows with JDBC activities inside a Transaction Group")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class JDBCTransactionParallelFlowCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(JDBCTransactionParallelFlowCheck.class);
    public static final String RULE_KEY = "JDBCTransactionParallelFlow";
    
    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();            
        if(process != null){
            for(Group group : process.getGroups()){
                LOG.debug("Group type ["+group.getType() + "] - ["+group.getName()+"]");
                if("localTX".equals(group.getType())){
                    LOG.debug("Local Transaction detected: ["+ group.getActivities() + "]");                
                    for(Activity activity : group.getActivities()){
                        LOG.debug("Activity name ["+activity.getName()+"]");
                        if(activity.hasParallelFlow()){
                            List<List<Activity>> flows = activity.checkFlowArray(false);
                            int flowJDBC = 0;
                            for(List<Activity> flow : flows){
                                boolean usingJDBC = false;
                                String flowString  = "";
                                for(Activity item : flow){
                                    flowString += item.getName() + " --> ";
                                    if(!usingJDBC && item.getType().contains("jdbc")){
                                        flowJDBC++;
                                        usingJDBC = true;
                                    }
                                   
                                }
                                LOG.debug("Flow ["+flowString+"]");
                            }
                            if(flowJDBC > 1){
                                reportIssueOnFile("JDBC Parallel flow inside a transaction group is not supported. So unexpected behavior in runtime will be generated",XmlHelper.getLineNumber(activity.getNode()));
                            }
                        }
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
