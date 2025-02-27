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

@Rule(key = JDBCWildCardCheck.RULE_KEY, name = "JDBC WildCard Check", priority = Priority.MAJOR, description = "This rule checks whether JDBC activities are using wildcards in the query. As a good coding practice, never use wildcards in JDBC queries.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class JDBCWildCardCheck extends AbstractProcessCheck {
private static final Logger LOG = LoggerFactory.getLogger(JDBCWildCardCheck.class);
    public static final String RULE_KEY = "JDBCWildcards";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        List<Activity> list = processSource.getProcessModel().getActivities();
        for (Activity activity : list) {
            String sqlStatement = activity.getProperty("sqlStatement");
            if (sqlStatement != null && sqlStatement.contains("*")) {
                reportIssueOnFile("WildCards should not be used in a JDBC Query. Use correct colomn names in JDBC query for activity " + activity.getName() + " from process " + processSource.getProcessModel().getName(),XmlHelper.getLineNumber(activity.getNode()));
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
