/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.check.process;


import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw6.model.Process;
import com.tibco.utils.bw6.model.Transition;
import java.util.List;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
    
@Rule(key = OnlyOneOtherwiseConditionCheck.RULE_KEY, name = "Only one Otherwise Condition Check", priority = Priority.MAJOR, description = "This rule checks multiple transition from an activity only one for the paths are for no matching condition because multiple ones are not supported and could lead to an unexpected runtime behavior",tags = {"bug"})
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class OnlyOneOtherwiseConditionCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(OnlyOneOtherwiseConditionCheck.class);
    public static final String RULE_KEY = "OnlyOneOtherwiseCheck";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        
        process.getActivities().forEach((activity) -> {            
            int numberOfOtherwiseChecks = 0;
            List<Transition> outcomingTransitionList = activity.getOutputTransitions();
            if(outcomingTransitionList != null){
                numberOfOtherwiseChecks = outcomingTransitionList.stream().map((tr) -> {
                    LOG.debug("Checking transition ["+tr.getFrom()+"] --- "+  tr.getConditionType() + "  ----> ["+tr.getTo()+"]");                    
                    return tr;
                }).filter((tr) -> ("SUCCESSWITHNOCONDITION".equals(tr.getConditionType()))).map((_item) -> 1).reduce(numberOfOtherwiseChecks, Integer::sum);
            }
            LOG.debug("Number of otherwise for activity ["+activity.getName()+"]: "+numberOfOtherwiseChecks);
            if (numberOfOtherwiseChecks > 1) {
                //TODO Add line number
                reportIssueOnFile("The transition from activity "+activity.getName() +" have more than one 'Success with no matching condition' and must have only one",1);
            }
        });
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
