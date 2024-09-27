/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.process;

import java.util.List;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = LogOnlyInSubprocessCheck.RULE_KEY, name = "Log Only in Subprocess Check", priority = Priority.MINOR, description = "If there is logging or auditing required at multiple points in your project, its advised to write logging and auditing code in a sub process and invoke this process from any point where this functionality is required. This rule checks whether LOG activity is used in sub process.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class LogOnlyInSubprocessCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(LogOnlyInSubprocessCheck.class);
    public static final String RULE_KEY = "LogSubprocess";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        if (!processSource.getProcessModel().isSubProcess()) {
            List<Activity> list = processSource.getProcessModel().getActivities();
            list.stream().filter(activity -> (activity.getType() != null && activity.getType().equals("bw.generalactivities.log"))).forEachOrdered(activity -> {
                reportIssueOnFile("The Log activity [" + activity.getName() + "] should be preferrably used in a sub process.  " + processSource.getProcessModel().getBasename() + " is not a subprocess.",XmlHelper.getLineNumber(activity.getNode()));
            });
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
