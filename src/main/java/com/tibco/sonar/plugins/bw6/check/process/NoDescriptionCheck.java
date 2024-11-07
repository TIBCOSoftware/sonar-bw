/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.Document;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.bw6.model.Process;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;

@Rule(key = NoDescriptionCheck.RULE_KEY, name = "No Process Description Check", priority = Priority.MINOR, description = "This rule checks if there is description specified for a process.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class NoDescriptionCheck extends AbstractProcessCheck {

    private static final Logger LOG = LoggerFactory.getLogger(NoDescriptionCheck.class);
    public static final String RULE_KEY = "ProcessNoDescription";
    public static final String DESCRIPTION_ELEMENT_NAME = "documentation";
    public static final String DESCRIPTION_ELEMENT_NAMESPACE = "http://docs.oasis-open.org/wsbpel/2.0/process/executable";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        LOG.debug("Process description for process [" + process.getBasename() + "]: " + process.getDescription());
        if (process.getDescription() == null
                || process.getDescription().isEmpty()) {
            reportIssueOnFile("Empty description for this process",XmlHelper.getLineNumber(process.getNode()));
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
