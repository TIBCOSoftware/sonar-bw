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
import com.tibco.utils.bw6.model.Process;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;

@Rule(key = ChoiceOtherwiseCheck.RULE_KEY, name = "Choice Condition with No Otherwise Check", priority = Priority.MAJOR, description = "This rule checks all activities input mapping for choice statement. As a coding best practice, the choice statement should always include the option otherwise.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class ChoiceOtherwiseCheck extends AbstractProcessCheck {

    private static final Logger LOG = LoggerFactory.getLogger(ChoiceOtherwiseCheck.class);
    public static final String RULE_KEY = "ChoiceWithNoOtherwise";

    @Override
    protected void validate(ProcessSource processSource) {

        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        List<Activity> activities = process.getActivities();
        activities.forEach(activity -> {
            String expr = activity.getExpression();
            if (expr != null && expr.contains("xsl:choose") && !expr.contains("xsl:otherwise")) {
                reportIssueOnFile("The choice statement in activity input of " + activity.getName() + " does not include the option otherwise",XmlHelper.getLineNumber(activity.getNode()));
            }

        });
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
