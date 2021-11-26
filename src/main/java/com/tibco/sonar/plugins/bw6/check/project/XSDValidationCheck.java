/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.check.project;

import com.tibco.sonar.plugins.bw6.check.AbstractProjectCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProjectSource;
import com.tibco.utils.bw6.model.Project;
import com.tibco.utils.bw6.model.XsdResource;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.sonar.api.batch.fs.InputFile;

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(
        key = XSDValidationCheck.RULE_KEY,
        name = "XSD Validation Check",
        description = "Check in the XSD inside the project are valid or have some errors",
        priority = Priority.MINOR )

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class XSDValidationCheck extends AbstractProjectCheck {

    public static final String RULE_KEY = "XSDValidation";

    private static final Logger LOG = Loggers.get(XSDValidationCheck.class);

    @Override
    public void validate(ProjectSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        Project project = resourceXml.getProject();
        LOG.debug("Checking project files");

        if (project != null) {
            for (XsdResource schema : project.getSchemas()) {
                InputFile schemaFile = resourceXml.getMap().getFile(schema);
                if(schemaFile != null){
                    String errorList = loadSchema(schemaFile);
                    if(errorList != null){
                         reportIssueOnFile("Detected errors on Schema [" + schemaFile.filename() + "] with message: [" + errorList + "]",schemaFile,1);
                    }
                }                
            }
        }

    }

    public static String loadSchema(InputFile file) {
        Schema schema = null;
        try {
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(language);
            schema = factory.newSchema(new StreamSource(file.inputStream()));
        } catch (Exception e) {
            return e.toString();
        }
        return null;
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
