/*
 * Copyright (C) 2016-2023. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.check.process;

import com.tibco.utils.bw6.model.Process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = ForEachGroupCheck.RULE_KEY, name = "For-Each Group Check", priority = Priority.INFO, description = "This rule checks the ForEach group. It is recommended to use For-Each activity input mapping instead of using For-Each/Iteration Group wherever possible. Do not use iteration groups just for mapping repeating elements.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.INFO)
public class ForEachGroupCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(ForEachGroupCheck.class);
    public static final String RULE_KEY = "ForEachGroup";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        if (process.isHasForEachGroup()) {
            reportIssueOnFile("For-Each group is used in process " + process.getBasename() + ". For performance reasons it is recommended to use For-Each in activity input mapping instead of using For-Each Group whenever possible. ");
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
