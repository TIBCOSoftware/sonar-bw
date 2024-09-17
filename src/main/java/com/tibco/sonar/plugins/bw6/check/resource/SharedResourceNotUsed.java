/*
 * Copyright (C) 2016-2023. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.check.resource;

import com.tibco.sonar.plugins.bw6.check.AbstractResourceCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.SharedResourceSource;
import com.tibco.utils.bw6.model.SharedResource;
import java.io.IOException;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(
        key = SharedResourceNotUsed.RULE_KEY,
        name = "Shared Resource not used",
        description = "Shared Resource not used",
        priority = Priority.MINOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class SharedResourceNotUsed extends AbstractResourceCheck {

    public static final String RULE_KEY = "SharedResourcesNotUsed";

    private static final Logger LOG = Loggers.get(SharedResourceNotUsed.class);

    @Override
    public void validate(SharedResourceSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        SharedResource resource = resourceXml.getResource();
        LOG.debug("Checking project resource files");
        FileSystem fileSystem = resourceXml.getFileSystem();
        if (fileSystem != null && resource != null) {
            Iterable<InputFile> files = fileSystem.inputFiles(fileSystem.predicates().all());
            if (files != null) {
                boolean found = false;
                for (InputFile file : files) {
                    LOG.debug("Analyzing file: " + file.filename());
                    if (!isDefinitionFile(resource, file)) {
                        try {
                            LOG.debug("Checking file contents: "+file.filename() + " ...");
                            if (file.contents().contains(resource.getName())) {
                                found = true;
                            }
                        } catch (IOException ex) {
                            LOG.warn("File not found", ex);
                        } catch (NullPointerException ex) {
                            LOG.warn("Catching NullPointerException", ex);
                        }
                    }
                }

                if (!found) {
                    LOG.warn("BW Shared Resource not used in the project scope");
                    reportIssueOnFile("Shared resource [" + resource.getName() + "] is not used in the project");
                }

            }
        }

        LOG.debug("Finished rule: " + this.getClass());
    }

    private boolean isDefinitionFile(SharedResource resource, InputFile file) {
        boolean out = false;
        if (resource != null && file != null) {
            String name = resource.getName();
            LOG.debug("Resource name: ["+name+"]");
            int lstIdxOf = name.lastIndexOf('.');
            if (lstIdxOf > 0) {
                name = name.substring(lstIdxOf + 1);
                LOG.debug("Resource name without extension: ["+name+"]");
                String fileName = file.filename();
                LOG.debug("File name to check with: ["+fileName+"]");
                lstIdxOf = fileName.indexOf('.');
                if (lstIdxOf > 0) {
                    fileName = fileName.substring(0, lstIdxOf);
                    LOG.debug("File name without extension to check with: ["+fileName+"]");
                    if (fileName.equals(name)) {
                        out = true;
                    }
                }
            }
        }
         LOG.debug("Comparision returns: ["+out+"]");
        return out;
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
