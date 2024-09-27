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
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Process;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = ParseXMLRenderXMLCheck.RULE_KEY, name = "Parse XML Activity using RenderXML output as input", priority = Priority.MINOR, description = "This rule checks for inefficiencies on using ParseXML activities using output for RenderXML activity as input when it should rely on Coertion to do same job ")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class ParseXMLRenderXMLCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(ParseXMLRenderXMLCheck.class);
    public static final String RULE_KEY = "ParseXMLRenderXMLActivity";

    
    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        LOG.debug("Number of services for process: ["+process.getBasename()+"]: "+process.getServices().size());
        List<Activity> activityList = process.getActivitiesByType("bw.xml.parsexml");
        if(activityList != null){
            for(Activity activity : activityList){
                checkActivity(activity, process);
            }
                
            }
        

        LOG.debug("Validation ended for rule: " + RULE_KEY);
    }

    private void checkActivity(Activity activity, Process process) {
        String expression = activity.getExpression();
        Pattern pattern = Pattern.compile(".*<xsl:value\\-of select=\"\\$([^\"]+)\"/>.*",Pattern.DOTALL | Pattern.MULTILINE);
        Matcher m = pattern.matcher(expression);
        if(m.matches()){
            String variable = m.group(1);

            List<Activity> renderXmlList = process.getActivitiesByType("bw.xml.renderxml");
            if(renderXmlList != null){
                for(Activity renderXml : renderXmlList){
                    if(variable.equals(renderXml.getName())){
                       reportIssueOnFile("The activity [" + renderXml.getName() + "] should be avoided to be included in the ParseXML activity",XmlHelper.getLineNumber(renderXml.getNode()));
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
    public Logger getLogger() {
       return LOG;
    }


    
}
