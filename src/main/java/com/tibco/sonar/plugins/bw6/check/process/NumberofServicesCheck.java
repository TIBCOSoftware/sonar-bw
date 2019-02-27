package com.tibco.sonar.plugins.bw6.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.ProcessSonarWayProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.sonar.plugins.bw6.violation.DefaultViolation;
import com.tibco.sonar.plugins.bw6.violation.Violation;
import com.tibco.utils.bw.model.Process;

@Rule(key = NumberofServicesCheck.RULE_KEY, name="Number of Exposed Services Check", priority = Priority.MAJOR, description = "This rule checks the number of exposed services within a process. It is a good design practice to construct not more than 5 services in the same process.")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.MAJOR)
public class NumberofServicesCheck extends AbstractProcessCheck{
	public static final String RULE_KEY = "NumberOfExposedServices";
	
	@Override
	protected void validate(ProcessSource processSource) {
		Process process = processSource.getProcessModel();
		if(process.getServices() != null && process.getServices().size() >5){
			String proc = process.getName();
			proc = proc.substring(proc.lastIndexOf(".")+1).concat(".bwp");
                        reportIssueOnFile("The process "+proc+" has too many services exposed, this reduces the process readablity and is not a good design pattern.");
		}
	}

}
