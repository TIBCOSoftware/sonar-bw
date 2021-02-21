/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
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
import com.tibco.utils.bw6.model.Process;
import java.util.List;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = ParseXMLBinaryCheck.RULE_KEY, name = "ParseXML using Binary", priority = Priority.MINOR, description = "ParseXML should use binary mode for performance assestment ")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class ParseXMLBinaryCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(ParseXMLBinaryCheck.class);
    public static final String RULE_KEY = "ParseXMLBinary";

    
    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        LOG.debug("Number of services for process: ["+process.getBasename()+"]: "+process.getServices().size());
        List<Activity> activityList = process.getActivitiesByType("bw.xml.parsexml");
        if(activityList != null){
            for(Activity activity : activityList){
                if("text".equals(activity.getProperty("inputStyle"))){
                      reportIssueOnFile("The activity [" + activity.getName() + "] should have binary format configured to performance issues",XmlHelper.getLineNumber(activity.getNode()));
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
