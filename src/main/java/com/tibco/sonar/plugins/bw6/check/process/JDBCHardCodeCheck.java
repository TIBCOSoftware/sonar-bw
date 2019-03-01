package com.tibco.sonar.plugins.bw6.check.process;

import java.util.List;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw.model.Activity;

@Rule(key = JDBCHardCodeCheck.RULE_KEY, name="JDBC HardCoded Check", priority = Priority.MAJOR, description = "This rule checks JDBC activities for hardcoded values for fields Timeout and MaxRows. Use Process property or Module property.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class JDBCHardCodeCheck extends AbstractProcessCheck {
	public static final String RULE_KEY = "JDBCHardCoded";

	@Override
	protected void validate(ProcessSource processSource) {
		List<Activity> list = processSource.getProcessModel().getActivities();
            for (Activity activity : list) {
                if(activity.getType() != null && activity.getType().contains("bw.jdbc.")){
                    
                    
                    if(activity.hasProperty("maxRows")){
                        reportIssueOnFile("The max rows setting in the JDBC activity "+activity.getName()+" is assigned a hardcoded value. It should be defined as Process property or Module property.");
                    }
                    if(activity.hasProperty("timeout")){
                        reportIssueOnFile("The timeout setting in the JDBC activity "+activity.getName()+" is assigned a harcoded value. It should be defined as Process property or Module property.");
                    }
                }
            }
	}
        
        @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }
}
