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
import com.tibco.utils.bw5.model.Transition;
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import java.util.List;
    
@Rule(key = NoOtherwiseConditionCheck.RULE_KEY, name = "No Otherwise Condition Check", priority = Priority.MAJOR, description = "This rule checks multiple transition from an activity one for the paths are for no matching condition to handle all posible outcomes of the decision execution")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class NoOtherwiseConditionCheck extends AbstractProcessCheck {

    private static final Logger LOG = LoggerFactory.getLogger(NoOtherwiseConditionCheck.class);
    public static final String RULE_KEY = "NoOtherwiseCheck";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        if(process != null){
            for(Activity activity : process.getActivities()){
                checkTransitionsFromActivity(process, activity);
            }
            checkTransitionsFromActivity(process,process.getStarter());
        }
    }



    private void checkTransitionsFromActivity(Process process, Activity activity) {
        boolean shouldHaveOtherwise = false;
        boolean haveOtherwise = false;
        List<Transition> outcomingTransitionList = process.getOutputTransitions(activity);
        if(outcomingTransitionList != null){
            for(Transition tr : outcomingTransitionList){
                if(tr != null){
                    LOG.debug("Checking transition ["+tr.getFrom()+"] --- "+  tr.getConditionType() + "  ----> ["+tr.getTo()+"]");
                    if("xpath".equals(tr.getConditionType())){
                        shouldHaveOtherwise = true;
                    }else if("otherwise".equals(tr.getConditionType())){
                        haveOtherwise = true;
                    }
                }
            }
        }
        LOG.debug("Checking transition for activity ["+ activity.getName()+"]");
        if(shouldHaveOtherwise && !haveOtherwise){
             reportIssueOnFile("The transition from activity "+ activity.getName() +" doesn't have an 'Sucess with no matching' transition to be able to handle all possible outcomes", XmlHelper.getLineNumber(activity.getNode()));
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
