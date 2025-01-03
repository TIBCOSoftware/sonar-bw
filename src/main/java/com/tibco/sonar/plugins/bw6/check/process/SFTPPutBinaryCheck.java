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
import com.tibco.utils.bw6.model.Process;
import java.util.List;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;

@Rule(key = SFTPPutBinaryCheck.RULE_KEY, name = "SFTP Put activity using Binary", priority = Priority.MINOR, description = "SFTP Put should use binary mode for avoiding encoding issues when transferring ")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class SFTPPutBinaryCheck extends AbstractProcessCheck {

    private static final Logger LOG = LoggerFactory.getLogger(SFTPPutBinaryCheck.class);
    public static final String RULE_KEY = "SFTPPutBinary";

    
    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        
        List<Activity> activityList = process.getActivitiesByType("bw.sftp.put");
        if(activityList != null){
            for(Activity activity : activityList){
                LOG.debug("Activity [SFTP Put] detected ["+activity.getName()+"] with binary ["+activity.getProperty("binary")+"]" );
                if(!"true".equals(activity.getProperty("binary"))){
                      reportIssueOnFile("The activity [" + activity.getName() + "] should use binary format configured to avoid encoding issues",XmlHelper.getLineNumber(activity.getNode()));
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
