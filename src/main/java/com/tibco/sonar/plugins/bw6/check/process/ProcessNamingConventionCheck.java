package com.tibco.sonar.plugins.bw6.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw6.model.Process;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.RuleProperty;

@Rule(key = ProcessNamingConventionCheck.RULE_KEY, name = "Process Naming Convention Check", priority = Priority.MINOR, description = "This rule ensure the naming convention for process names")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class ProcessNamingConventionCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(ProcessNamingConventionCheck.class);
    public static final String RULE_KEY = "ProcessNamingConvention";

    @RuleProperty(key = "pattern", description = "Regular Expression Process Name should meet", defaultValue = "[A-Z][a-z]+\\.bwp", type = "TEXT")
    protected String regExpPattern;

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();

        LOG.debug("Process Name: ["+process.getName()+"]");
        if (!process.getBasename().matches(regExpPattern)) {
            reportIssueOnFile("The process " + process.getBasename() + " doesn't match the process naming convention ["+regExpPattern+"]");
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

    /**
     * @return the regExpPattern
     */
    public String getRegExpPattern() {
        return regExpPattern;
    }

    /**
     * @param regExpPattern the regExpPattern to set
     */
    public void setRegExpPattern(String regExpPattern) {
        this.regExpPattern = regExpPattern;
    }
}
