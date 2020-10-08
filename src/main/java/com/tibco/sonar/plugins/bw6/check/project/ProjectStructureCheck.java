/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.check.project;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tibco.sonar.plugins.bw6.check.AbstractProjectCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProjectSource;
import com.tibco.utils.bw6.model.Project;
import java.io.File;


import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;

@Rule(
        key = ProjectStructureCheck.RULE_KEY,
        name = "Project Structure Check",
        description = "Check that an application module has the right structure",
        priority = Priority.MINOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class ProjectStructureCheck extends AbstractProjectCheck {

    public static final String RULE_KEY = "ProjectStructure";

    private static final Logger LOG = Loggers.get(ProjectStructureCheck.class);

    @RuleProperty(key = "jsonTemplate", description = "JSON Template that defines the valid structure", defaultValue = "{\n"
            + "    \"name\" : \"\",\n"
            + "    \"modelVersion\": \"1\",\n"
            + "    \"structure\" : [\n"
            + "        {\n"
            + "            \"namePattern\" : \".*\",\n"
            + "            \"type\" : \"file\",\n"
            + "            \"structure\" : [\n"
            + "\n"
            + "            ]\n"
            + "        }\n"
            + "\n"
            + "    ]\n"
            + "\n"
            + "}", type = "TEXT")
    protected String jsonStructure;

    @Override
    public void validate(ProjectSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        Project project = resourceXml.getProject();
        LOG.debug("Checking project files");

        JsonObject object = new Gson().fromJson(jsonStructure, JsonObject.class);
        if (project != null && project.isIsApplicationModule()) {
            File rootProject = project.getFile();

            File[] childFiles = rootProject.listFiles();

            if (childFiles != null) {
                for (File child : childFiles) {
                    check(child, object);
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

    private void check(File child, JsonObject object) {

        File[] childFiles = child.listFiles();
        for (File childItem : childFiles) {
            boolean valid = false;
            JsonArray structureArray = object.getAsJsonArray("structure");
            for (int i = 0; i < structureArray.size(); i++) {
                JsonObject structure = structureArray.get(i).getAsJsonObject();

                if (structure != null) {
                    String namePattern = structure.get("namePattern").getAsString();
                    String type = structure.get("type").getAsString();
                    if (type != null && (("file".equals(type) && childItem.isFile()) || ("folder".equals(type) && childItem.isDirectory()))) {
                        if (child.getName().matches(namePattern)) {
                            valid = true;
                            if (childItem.isDirectory()) {
                                check(childItem, structure);
                            }
                        }
                    }
                }
            }
            
            if(!valid){
                reportIssueOnFile("File ["+childItem.getAbsolutePath()+"] is not allowed here");
            }
        }
    }

}
