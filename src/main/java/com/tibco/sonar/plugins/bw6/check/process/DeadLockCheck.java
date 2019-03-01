package com.tibco.sonar.plugins.bw6.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;

@Rule(key = DeadLockCheck.RULE_KEY, name="Deadlock Detection Check", priority = Priority.BLOCKER, description = "There are many situations in which deadlocks can be created between communicating web services. This rule checks for deadlocks and infinite loops in BW6 process design.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.BLOCKER)
public class DeadLockCheck  extends AbstractProcessCheck {
	public static final String RULE_KEY = "DeadlockDetection";

	@Override
	protected void validate(ProcessSource processSource) {
		// whole logic is written in analyseDeadLock method of ProcessRuleSensor as this validation has to be taken place across processes
		
	}
        
        @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }
}
