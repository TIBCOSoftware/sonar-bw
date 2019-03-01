package com.tibco.sonar.plugins.bw6.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw.model.Process;
import org.sonar.check.RuleProperty;

@Rule(key = NumberofServicesCheck.RULE_KEY, name="Number of Exposed Services Check", priority = Priority.MAJOR, description = "This rule checks the number of exposed services within a process. It is a good design practice to construct not more than 5 services in the same process.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class NumberofServicesCheck extends AbstractProcessCheck{
	public static final String RULE_KEY = "NumberOfExposedServices";
	
        @RuleProperty(key = "maxServices", description = "Threshold of services to be considered excessive for a single process", defaultValue = "5",type = "INTEGER")
        private int maxServices;
  
        
	@Override
	protected void validate(ProcessSource processSource) {
		Process process = processSource.getProcessModel();
		if(process.getServices() != null && process.getServices().size() > maxServices){
                        reportIssueOnFile("The process "+process.getBasename()+" has too many services exposed, this reduces the process readablity and is not a good design pattern.");
		}
	}

        @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }
}
