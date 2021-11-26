/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.check.project;

import com.tibco.sonar.plugins.bw6.check.AbstractProjectCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProjectSource;
import com.tibco.utils.bw6.model.Binding;
import com.tibco.utils.bw6.model.Component;
import com.tibco.utils.bw6.model.Policy;
import com.tibco.utils.bw6.model.Project;
import com.tibco.utils.bw6.model.Service;

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(
        key = BindingShouldNotHaveHTTPBasicPolicyAssociatedCheck.RULE_KEY,
        name = "HTTP Binding should not have a HTTPBasicPolicy associated",
        tags = {"security"},
        description = "To ensure that the communications are authentified all input connections should check that the binding has a policy associated",
        priority = Priority.MINOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class BindingShouldNotHaveHTTPBasicPolicyAssociatedCheck extends AbstractProjectCheck {

    public static final String RULE_KEY = "BindingShouldNotHaveHTTPBasicPolicyAssociated";

    private static final Logger LOG = Loggers.get(BindingShouldNotHaveHTTPBasicPolicyAssociatedCheck.class);

    @Override
    public void validate(ProjectSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        Project project = resourceXml.getProject();
        LOG.debug("Checking project files");
        if (project != null && project.getComponents() != null) {
            LOG.debug("Properties are not null");
            for (Component comp : project.getComponents()) {
                if (comp.getServices() != null) {
                    for (Service service : comp.getServices()) {
                        if (service != null) {
                            Binding binding = service.getBinding();
                            if (binding != null) {
                                LOG.debug("Binding Transport Binding Type: [" + binding.getTransportBindingType() + "] and Policies Sets: " + binding.getProperty("policySets"));
                                if ("HTTP".equals(binding.getTransportBindingType()) && binding.getProperty("policySets") != null) {
                                    String policyName = binding.getProperty("policySets");
                                    LOG.debug("Detected policy name: " + policyName);
                                    Policy p = project.getPolicyByName(policyName);
                                    if (p != null) {
                                        LOG.debug("Retrieved policy data from policy name: " + p.getName());
                                        if ("template_2010:BasicAuthentication".equals(p.getType())) {
                                            reportIssueOnFile("HTTP Binding of this component [" + comp.getName() + "] should not use HTTP Basic Authentication as their authentication method");
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }

    @Override
    public org.sonar.api.utils.log.Logger getLogger() {
        return LOG;
    }

}