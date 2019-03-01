package com.tibco.sonar.plugins.bw6.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw.model.Process;
import org.sonar.check.RuleProperty;

@Rule(key = NumberofActivitiesCheck.RULE_KEY, name="Number of Activities Check", priority = Priority.MINOR, description = "This rule checks the number of activities within a process, too many activities reduces the process readability")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class NumberofActivitiesCheck extends AbstractProcessCheck{
	public static final String RULE_KEY = "NumberOfActivities";
	
        @RuleProperty(key = "maxActivities", description = "Threshold of activities to be considered excessive for a single process", defaultValue = "24",type = "INTEGER")
        private int maxActivities;
        
	@Override
	protected void validate(ProcessSource processSource) {
		Process process = processSource.getProcessModel();
		int activityCount = process.getActivities().size();
                if (activityCount > maxActivities) {
                        reportIssueOnFile("The process "+process.getBasename()+" has too many activities, this reduces the process readablity");
                }
	}
        
        @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }
}
