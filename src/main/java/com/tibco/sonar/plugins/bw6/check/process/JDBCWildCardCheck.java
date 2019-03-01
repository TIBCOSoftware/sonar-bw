package com.tibco.sonar.plugins.bw6.check.process;

import java.util.List;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw.model.Activity;

@Rule(key = JDBCWildCardCheck.RULE_KEY, name = "JDBC WildCard Check", priority = Priority.MAJOR, description = "This rule checks whether JDBC activities are using wildcards in the query. As a good coding practice, never use wildcards in JDBC queries.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class JDBCWildCardCheck extends AbstractProcessCheck {

    public static final String RULE_KEY = "JDBCWildcards";

    @Override
    protected void validate(ProcessSource processSource) {
        List<Activity> list = processSource.getProcessModel().getActivities();
        for (Activity activity : list) {
            String sqlStatement = activity.getProperty("sqlStatement");
            if (sqlStatement != null && sqlStatement.contains("*")) {
                reportIssueOnFile("WildCards should not be used in a JDBC Query. Use correct colomn names in JDBC query for activity " + activity.getName() + " from process " + processSource.getProcessModel().getName());
            }
        }

    }
    
    @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }
}
