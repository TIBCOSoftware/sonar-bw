/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.common.helper.XmlHelper;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = ExceptionHandlingCheck.RULE_KEY, name = "Exception handling check", priority = Priority.MINOR, description = "Check if exceptions are handled in component process.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class ExceptionHandlingCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(ExceptionHandlingCheck.class);
    public static final String RULE_KEY = "ExceptionHandlingCheck";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        if (!processSource.getProcessModel().isSubProcess() && processSource.getProcessModel().getCatchcount() == 0) {
            reportIssueOnFile("Exception is not handled in component process: " + processSource.getProcessModel().getName(),XmlHelper.getLineNumber(processSource.getProcessModel().getNode()));
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
