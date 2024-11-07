/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.project;

import com.tibco.sonar.plugins.bw6.check.AbstractProjectCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProjectSource;
import com.tibco.utils.bw6.model.Project;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(
        key = JKSValidationCheck.RULE_KEY,
        name = "JKS Validation Checks",
        tags = {"security"},
        description = "Check the JKS inside the project to see if they've been expired or if they're autosigned",
        priority = Priority.MAJOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class JKSValidationCheck extends AbstractProjectCheck {

    public static final String RULE_KEY = "JKSValidation";

    private static final Logger LOG = LoggerFactory.getLogger(JKSValidationCheck.class);

   
    @Override
    public void validate(ProjectSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        Project project = resourceXml.getProject();
        LOG.debug("Checking project files");
        if(project != null && project.getKeystores() != null){
            LOG.debug("Keystores are not null");
            for(KeyStore keystore : project.getKeystores()){
                try {   
                    LOG.debug("Checking keystore: "+keystore.toString());
                    Enumeration<String> aliases = keystore.aliases();
                    while (aliases.hasMoreElements()){
                        String alias = aliases.nextElement();
                        LOG.debug("Checking certificate alias: "+alias);
                        Certificate cert = keystore.getCertificate(alias);
                        if(cert instanceof X509Certificate){
                            X509Certificate x509cert = (X509Certificate)cert;
                            validateCertAndReportIssue(x509cert, alias);

                        }
                    }
                } catch (KeyStoreException ex) {
                    LOG.warn("Error reading the KeyStore", ex);
                }
            }
        }
            
        
    }

    private void validateCertAndReportIssue(X509Certificate x509cert, String alias) {
        try {
            x509cert.checkValidity();
        } catch (CertificateExpiredException ex) {
            reportIssueOnFile("Certificate alias ["+ alias +"] is expired:  " + ex.getMessage());
        } catch (CertificateNotYetValidException ex) {
            reportIssueOnFile("Certificate alias ["+ alias +"] is not valid:  " + ex.getMessage());
        }
        String subjectDN = x509cert.getSubjectX500Principal() == null? "" : x509cert.getSubjectX500Principal().getName();
        String issuerDN = x509cert.getIssuerX500Principal()  == null? "" : x509cert.getIssuerX500Principal().getName();
        if(subjectDN.equals(issuerDN)){
                reportIssueOnFile("Certificate ["+ alias +"] is autosigned");
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
