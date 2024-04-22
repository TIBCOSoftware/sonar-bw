/*
 * Copyright (C) 2016-2023. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.Document;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw6.helper.XmlHelper;
import com.tibco.utils.bw6.model.Process;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = DefaultTargetNamespaceCheck.RULE_KEY, name = "Default Target Namespace Check", priority = Priority.INFO, description = "This rule checks if process namespace is the default one generated by the tool.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.INFO)
public class DefaultTargetNamespaceCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(DefaultTargetNamespaceCheck.class);
    public static final String RULE_KEY = "DefaultTargetNamespace";
    
    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();            
        LOG.debug("Target namespace for process [" + process.getBasename() + "]: " + process.getNamespace());
        if (process.getNamespace() != null && process.getNamespace().startsWith("http://xmlns.example.com/")) {
            //TODO Add line here
            reportIssueOnFile("Default target namespace is used for this process",XmlHelper.getLineNumber(process.getNode()));
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
