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

@Rule(key = NumberofServicesCheck.RULE_KEY, name = "Number of Exposed Services Check", priority = Priority.MINOR, description = "This rule checks the number of exposed services within a process. It is a good design practice to construct not more than 5 services in the same process.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class NumberofServicesCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(NumberofServicesCheck.class);
    public static final String RULE_KEY = "NumberOfExposedServices";

    @RuleProperty(key = "maxServices", description = "Threshold of services to be considered excessive for a single process", defaultValue = "5", type = "INTEGER")
    protected int maxServices;

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        LOG.debug("Number of services for process: ["+process.getBasename()+"]: "+process.getServices().size());
        if (process.getServices() != null && process.getServices().size() > maxServices) {
            reportIssueOnFile("The process " + process.getBasename() + " has too many services exposed, this reduces the process readablity and is not a good design pattern.");
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
     * @return the maxServices
     */
    public int getMaxServices() {
        return maxServices;
    }

    /**
     * @param maxServices the maxServices to set
     */
    public void setMaxServices(int maxServices) {
        this.maxServices = maxServices;
    }
    
    
}
