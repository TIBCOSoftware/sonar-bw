/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.process;

import java.util.List;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;

@Rule(key = JDBCHardCodeCheck.RULE_KEY, name = "JDBC HardCoded Check", priority = Priority.MINOR, description = "This rule checks JDBC activities for hardcoded values for fields Timeout and MaxRows. Use Process property or Module property.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class JDBCHardCodeCheck extends AbstractProcessCheck {

    private static final Logger LOG = LoggerFactory.getLogger(JDBCHardCodeCheck.class);
    public static final String RULE_KEY = "JDBCHardCoded";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        List<Activity> list = processSource.getProcessModel().getActivities();
        for (Activity activity : list) {
            if (activity.getType() != null && activity.getType().contains("bw.jdbc.")) {
                LOG.debug("JDBC activity detected!");
                
                if (activity.hasProperty("maxRows")) {
                    reportIssueOnFile("The max rows setting in the JDBC activity " + activity.getName() + " is assigned a hardcoded value. It should be defined as Process property or Module property.",XmlHelper.getLineNumber(activity.getNode()));
                }
                if (activity.hasProperty("timeout")) {
                    reportIssueOnFile("The timeout setting in the JDBC activity " + activity.getName() + " is assigned a harcoded value. It should be defined as Process property or Module property.",XmlHelper.getLineNumber(activity.getNode()));
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
