package com.tibco.sonar.plugins.bw6.check.project;

import com.tibco.sonar.plugins.bw6.check.AbstractProjectCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProjectSource;
import com.tibco.utils.bw6.model.JSONResource;
import com.tibco.utils.bw6.model.Project;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.sonar.api.batch.fs.InputFile;

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(
        key = SwaggerValidationCheck.RULE_KEY,
        name = "Swagger Validation Check",
        description = "Check if the Swagger files inside the project are valid or have some errors",
        priority = Priority.MINOR)

@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class SwaggerValidationCheck extends AbstractProjectCheck {

    public static final String RULE_KEY = "SwaggerValidation";

    private static final Logger LOG = Loggers.get(SwaggerValidationCheck.class);

    @Override
    public void validate(ProjectSource resourceXml) {
        LOG.debug("Started rule: " + this.getClass());
        Project project = resourceXml.getProject();
        LOG.debug("Checking project files");

        if (project != null) {
            for (JSONResource schema : project.getJsonSchemas()) {
                InputFile schemaFile = resourceXml.getMap().getFile(schema);
                if(schemaFile != null){
                    String errorList = loadSchema(schemaFile);
                    if(errorList != null){
                         reportIssueOnFile("Detected errors on Swagger [" + schemaFile.filename() + "] with message: [" + errorList + "]",schemaFile,1);
                    }
                }                
            }
        }

    }

    public static String loadSchema(InputFile file) {
        String response = null;        
        try{
        Swagger swagger = new SwaggerParser().read(file.absolutePath());
        }catch(Exception ex){
            response = ex.getMessage();
        }        
        return response;
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
