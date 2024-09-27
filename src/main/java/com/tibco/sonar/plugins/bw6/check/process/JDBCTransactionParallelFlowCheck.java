/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Group;
import com.tibco.utils.bw6.model.Process;
import java.util.List;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = JDBCTransactionParallelFlowCheck.RULE_KEY, name = "JDBC Transaction Parallel Flow ", priority = Priority.MAJOR, description = "This rule checks if there is no parallel flows with JDBC activities inside a Transaction Group", tags = {"bug"})
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
                    checkParallelInTransaction(group);


                }
            }
        }
        LOG.debug("Validation ended for rule: " + RULE_KEY);
    }

    private void checkParallelInTransaction(Group group) {
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

    @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }

    @Override
    public Logger getLogger() {
        return LOG;
    }
}
