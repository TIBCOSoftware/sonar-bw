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
import static com.tibco.utils.bw6.constants.BwpModelConstants.BPWSEXIT;
import static com.tibco.utils.bw6.constants.BwpModelConstants.BPWSREPLY;
import static com.tibco.utils.bw6.constants.BwpModelConstants.BPWSRETHROW;
import static com.tibco.utils.bw6.constants.BwpModelConstants.BPWSTHROW;
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Process;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = ListFileActivityToCheckFileExistenceCheck.RULE_KEY, name = "Usage of List File activity to check file existence", priority = Priority.MINOR, description = "Using List File activity to check if a single file exists is less performant than using ReadFile without fileContent check")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class ListFileActivityToCheckFileExistenceCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(ListFileActivityToCheckFileExistenceCheck.class);
    public static final String RULE_KEY = "ListFileActivityToCheckFileExistence";
    
    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();            
        if(process != null){
            List<Activity> fileListActivities = process.getActivitiesByType("bw.file.list");
            if(fileListActivities != null){
                for(Activity act : fileListActivities ){
                    
                    String expression = act.getExpression();
                     String fileTest = null;
                    if(expression != null){
                        Pattern pat = Pattern.compile("<fileName><xsl:value-of select=\"([^\"]+)\"/></fileName>");
                        Matcher m = pat.matcher(expression);
                        if(m != null && m.find()){
                            fileTest = m.group(1);
                            fileTest = fileTest.replaceAll(Pattern.quote("&quot;"), "");
                        }
                    }
                   if(fileTest == null){
                        fileTest = act.getProperty("fileName"); 
                   }
                    
                    if(fileTest != null){
                        if(FilenameUtils.getExtension(fileTest) != null && !"".equals(FilenameUtils.getExtension(fileTest))){
                            reportIssueOnFile("ListFiles Activity with name ["+act.getName()+"] is being used for check for a single file existence. This should be replaced for a ReadFile activity with no check content",XmlHelper.getLineNumber(act.getNode()));
                        }
                    }
                }
            }
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

    private boolean isActivityEnd(String type) {
        return type != null && (type.equals(BPWSRETHROW) || type.equals(BPWSREPLY) || type.equals(BPWSEXIT) || type.equals(BPWSTHROW));
    }
}
