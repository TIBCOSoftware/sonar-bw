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
import com.tibco.utils.bw6.model.Binding;
import com.tibco.utils.bw6.model.Process;
import com.tibco.utils.bw6.model.Reference;
import com.tibco.utils.bw6.model.Service;
import java.util.Map.Entry;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;
    
@Rule(key = HttpClientMustBeUsedinHTTPBindingCheck.RULE_KEY, name = "HttpClient must be used in HTTP Binding", priority = Priority.MINOR, description = "HTTP Binding should have an HTTP Client Resource")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class HttpClientMustBeUsedinHTTPBindingCheck extends AbstractProcessCheck {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientMustBeUsedinHTTPBindingCheck.class);
    public static final String RULE_KEY = "HttpClientMustBeUsedinHTTPBinding";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        if(process != null){
            for(Entry<String,Service> entry : process.getProcessReferenceServices().entrySet()){
                Service reference = entry.getValue();
                if(reference instanceof Reference){
                    checkReference(reference);
                }
            }
        }
        LOG.debug("Start validation for rule: " + RULE_KEY);
        

    }

    private void checkReference(Service reference) {
        LOG.debug("Reference detected: "+ reference.getName());
        Binding binding = reference.getBinding();
        if(binding != null){
            LOG.debug("Binding detected type: "+ binding.getTransportBindingType());
            if("HTTP".equals(binding.getTransportBindingType()) && !(binding.getProperty("httpClient")  != null && !"".equals(binding.getProperty("httpClient")) )){
                reportIssueOnFile("HTTP Binding ["+ reference.getName()+"] has no HTTP Client Resource configured");
            }
        }
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
