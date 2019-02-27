package com.tibco.sonar.plugins.bw6.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.ProcessSonarWayProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.sonar.plugins.bw6.violation.DefaultViolation;
import com.tibco.sonar.plugins.bw6.violation.Violation;

@Rule(key = ExceptionHandlingCheck.RULE_KEY, name="Exception handling check", priority = Priority.MAJOR, description = "Check if exceptions are handled in component process.")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.MAJOR)
public class ExceptionHandlingCheck extends AbstractProcessCheck{
	public static final String RULE_KEY = "Exception handling check";
	
	@Override
	protected void validate(ProcessSource processSource) {
		// TODO Auto-generated method stub
		if(!processSource.getProcessModel().isSubProcess()){
			if(processSource.getProcessModel().getCatchcount()== 0){
				Violation violation = new DefaultViolation(getRule(),
						1,
						"Exception is not handled in component process: "+ processSource.getProcessModel().getName());
				processSource.addViolation(violation);				
			}
		}		
	}	
   
}
