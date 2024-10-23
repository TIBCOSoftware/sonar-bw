/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw5.check.process;

import com.tibco.sonar.plugins.bw5.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw5.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw5.source.ProcessSource;
import com.tibco.utils.bw5.model.Activity;
import com.tibco.utils.bw5.model.Process;
import com.tibco.utils.common.helper.XmlHelper;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Rule(key = RenderXMLPrettyPrintCheck.RULE_KEY, name = "tib:render-xml input using pretty print", priority = Priority.MINOR, description = "This rule checks for inefficiencies on using tib:render-xml function specifying the pretty-print option to true")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class RenderXMLPrettyPrintCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(RenderXMLPrettyPrintCheck.class);
    public static final String RULE_KEY = "RenderXmlPrettyPrint";
    
    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();            
        if(process != null){
            for(Activity activity : process.getActivities()){
                checkActivity(activity);
            }
        }
        LOG.debug("Validation ended for rule: " + RULE_KEY);
    }

    private void checkActivity(Activity activity) {
        LOG.debug("Activty type ["+ activity.getType() + "] - ["+ activity.getName()+"]");

        String expression = activity.getInputBindings().getNodeValue();
        if(expression != null && expression.contains("\"tib:render-xml(")){

            Pattern pat = Pattern.compile(".*(tib\\:render-xml\\([^\\,><]+?\\,[^\\,><]+?\\,([^\\)><]+?)\\)\\)).*",Pattern.DOTALL | Pattern.MULTILINE);
            Matcher mt = pat.matcher(expression);
            if(mt.matches()){
                String renderXmlExpression = mt.group(2);
                String wholeExpression = mt.group(1);

                if(renderXmlExpression != null && renderXmlExpression.endsWith("true(") ){
                    LOG.debug("Whole epxression: "+ wholeExpression);
                    LOG.debug("Render XML Expression: "+ renderXmlExpression);
                    reportIssueOnFile("render-xml function in activity ["+ activity.getName()+"] should avoid to use pretty-print option for performance",XmlHelper.getLineNumber(activity.getNode()));
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
