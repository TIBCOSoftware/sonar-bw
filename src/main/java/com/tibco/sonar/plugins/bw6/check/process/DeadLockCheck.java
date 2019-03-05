package com.tibco.sonar.plugins.bw6.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = DeadLockCheck.RULE_KEY, name = "Deadlock Detection Check", priority = Priority.BLOCKER, description = "There are many situations in which deadlocks can be created between communicating web services. This rule checks for deadlocks and infinite loops in BW6 process design.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.BLOCKER)
public class DeadLockCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(DeadLockCheck.class);
    public static final String RULE_KEY = "DeadlockDetection";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        // whole logic is written in analyseDeadLock method of ProcessRuleSensor as this validation has to be taken place across processes
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
