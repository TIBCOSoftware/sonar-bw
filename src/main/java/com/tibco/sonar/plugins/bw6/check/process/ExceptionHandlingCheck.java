package com.tibco.sonar.plugins.bw6.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;

@Rule(key = ExceptionHandlingCheck.RULE_KEY, name="Exception handling check", priority = Priority.MAJOR, description = "Check if exceptions are handled in component process.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class ExceptionHandlingCheck extends AbstractProcessCheck{
        
	public final static String RULE_KEY = "Exception handling check";
	
	@Override
	protected void validate(ProcessSource processSource) {
		if(!processSource.getProcessModel().isSubProcess()){
			if(processSource.getProcessModel().getCatchcount()== 0){
                            reportIssueOnFile("Exception is not handled in component process: "+ processSource.getProcessModel().getName(), 1);			
			}
		}		
	}	
   @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }
}
