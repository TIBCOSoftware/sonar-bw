package com.tibco.sonar.plugins.bw6.check.process;

import java.util.List;
import java.util.Map;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.NodeList;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import static com.tibco.sonar.plugins.bw6.check.process.CheckpointAfterHttpCheck.RULE_KEY;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Group;
import com.tibco.utils.bw6.model.Process;
import com.tibco.utils.bw6.model.Transition;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = ProcessWithoutTestCheck.RULE_KEY, name = "Process without Unit Test file", priority = Priority.INFO, description = "This rule checks that all processes should have at least one test file")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.INFO)
public class ProcessWithoutTestCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(ProcessWithoutTestCheck.class);
    public static final String RULE_KEY = "ProcessWithoutTest";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        if(!process.isHasTest()){
            reportIssueOnFile("Process has not a Test File detected",1);
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
