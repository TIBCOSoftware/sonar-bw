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
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;

@Rule(key = ParseXMLFromRenderCheck.RULE_KEY, name = "Parse XML Activity using tib:render-xml input", priority = Priority.MINOR, description = "This rule checks for inefficiencies on using ParseXML activities using tib:render-xml as input when it should rely on Coertion to do same job")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class ParseXMLFromRenderCheck extends AbstractProcessCheck {

    private static final Logger LOG = LoggerFactory.getLogger(ParseXMLFromRenderCheck.class);
    public static final String RULE_KEY = "ParseXMLFromRender";
    
    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();            
        if(process != null){
            for(Activity activity : process.getActivitiesByType("bw.xml.parsexml")){
                LOG.debug("Activty type ["+activity.getType() + "] - ["+activity.getName()+"]");
                
                String expression = activity.getExpression();
                if(expression != null && expression.contains("\"tib:render-xml(")){
                    reportIssueOnFile("ParseXML activity ["+activity.getName()+"] should be avoided as it is using for an implicit coertion",XmlHelper.getLineNumber(activity.getNode()));
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
