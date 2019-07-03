/*
 * SonarQube Java
 * Copyright (C) 2012 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
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
import com.tibco.utils.bw6.helper.XmlHelper;
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
