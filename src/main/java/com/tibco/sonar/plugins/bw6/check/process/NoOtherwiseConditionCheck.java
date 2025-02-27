/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.process;


import com.tibco.utils.common.helper.XmlHelper;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Process;
import com.tibco.utils.bw6.model.Transition;
import java.util.List;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;
    
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
                checkTransitionsFromActivity(activity);
            }
        }
    }

    private void checkTransitionsFromActivity(Activity activity) {
        boolean shouldHaveOtherwise = false;
        boolean haveOtherwise = false;
        List<Transition> outcomingTransitionList = activity.getOutputTransitions();
        if(outcomingTransitionList != null){
            for(Transition tr : outcomingTransitionList){
                if(tr != null){
                    LOG.debug("Checking transition ["+tr.getFrom()+"] --- "+  tr.getConditionType() + "  ----> ["+tr.getTo()+"]");
                    if("SUCCESSWITHCONDITION".equals(tr.getConditionType())){
                        shouldHaveOtherwise = true;
                    }else if("SUCCESSWITHNOCONDITION".equals(tr.getConditionType())){
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
