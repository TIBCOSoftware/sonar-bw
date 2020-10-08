/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.check.process;

import com.tibco.utils.bw6.model.Process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw6.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Group;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = UnneededGroupCheck.RULE_KEY, name = "Uneeded Group Check", priority = Priority.MINOR, description = "Some times developers tends to add additional groups where they are not needed.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class UnneededGroupCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(UnneededGroupCheck.class);
    public static final String RULE_KEY = "UnneededGroup";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        
        for(Group group : process.getGroups()){
            
                checkGroupInside(group);
                
            
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

    private void checkGroupInside(Group group) {
        if(group != null){
            //Start, End and another Group
            if(group.getActivities().size() == 1){
                for(Activity activity : group.getActivities()){
                    if(activity instanceof Group){
                        Group gTmp = (Group)activity;
                        if(group.getType() != null && group.getType().equals(gTmp.getType())){
                            reportIssueOnFile("Uneeded group has been detected ["+gTmp.getName()+"] ",XmlHelper.getLineNumber(gTmp.getNode()));
                        }
                    }
                }
            }
        }
    }
}
