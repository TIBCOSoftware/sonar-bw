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
import com.tibco.utils.bw6.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Process;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

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
                LOG.debug("Activty type ["+activity.getType() + "] - ["+activity.getName()+"]");
                
                String expression = activity.getExpression();
                if(expression != null && expression.contains("\"tib:render-xml(")){
                    
                    Pattern pat = Pattern.compile(".*(tib\\:render-xml\\([^\\,><]+?\\,[^\\,><]+?\\,([^\\)><]+?)\\)\\)).*",Pattern.DOTALL | Pattern.MULTILINE);
                    Matcher mt = pat.matcher(expression);
                    if(mt.matches()){
                        String renderXmlExpression = mt.group(2);
                        String wholeExpression = mt.group(1);
                        
                        if(renderXmlExpression != null && renderXmlExpression.endsWith("true(") ){
                            LOG.debug("Whole epxression: "+ wholeExpression);
                            LOG.debug("Render XML Expression: "+ renderXmlExpression);
                            reportIssueOnFile("render-xml function in activity ["+activity.getName()+"] should avoid to use pretty-print option for performance",XmlHelper.getLineNumber(activity.getNode()));
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
}
