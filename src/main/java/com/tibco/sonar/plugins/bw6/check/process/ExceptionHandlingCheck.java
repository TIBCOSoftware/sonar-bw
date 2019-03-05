package com.tibco.sonar.plugins.bw6.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = ExceptionHandlingCheck.RULE_KEY, name = "Exception handling check", priority = Priority.MAJOR, description = "Check if exceptions are handled in component process.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class ExceptionHandlingCheck extends AbstractProcessCheck {

    private final static Logger LOG = Loggers.get(ExceptionHandlingCheck.class);
    public final static String RULE_KEY = "Exception handling check";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        if (!processSource.getProcessModel().isSubProcess()) {
            if (processSource.getProcessModel().getCatchcount() == 0) {
                reportIssueOnFile("Exception is not handled in component process: " + processSource.getProcessModel().getName());
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
