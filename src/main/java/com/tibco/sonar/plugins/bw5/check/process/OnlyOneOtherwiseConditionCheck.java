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
    
@Rule(key = OnlyOneOtherwiseConditionCheck.RULE_KEY, name = "Only one Otherwise Condition Check", priority = Priority.MAJOR, description = "This rule checks multiple transition from an activity only one for the paths are for no matching condition because multiple ones are not supported and could lead to an unexpected runtime behavior",tags = {"bug"})
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class OnlyOneOtherwiseConditionCheck extends AbstractProcessCheck {

    private static final Logger LOG = LoggerFactory.getLogger(OnlyOneOtherwiseConditionCheck.class);
    public static final String RULE_KEY = "OnlyOneOtherwiseCheck";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();

        process.getActivities().forEach(activity -> checkActivity(activity, process));
        checkActivity(process.getStarter(),process);
    }

    private void checkActivity(Activity activity, Process process) {
        int numberOfOtherwiseChecks = 0;
        List<Transition> outcomingTransitionList = process.getOutputTransitions(activity);
        if(outcomingTransitionList != null){
            numberOfOtherwiseChecks = outcomingTransitionList.stream().map(tr -> {
                LOG.debug("Checking transition ["+tr.getFrom()+"] --- "+  tr.getConditionType() + "  ----> ["+tr.getTo()+"]");
                return tr;
            }).filter(tr -> ("otherwise".equals(tr.getConditionType()))).map(item -> 1).reduce(numberOfOtherwiseChecks, Integer::sum);
        }
        LOG.debug("Number of otherwise for activity ["+ activity.getName()+"]: "+numberOfOtherwiseChecks);
        if (numberOfOtherwiseChecks > 1) {
            reportIssueOnFile("The transition from activity "+ activity.getName() +" have more than one 'Success with no matching condition' and must have only one", XmlHelper.getLineNumber(activity.getNode()));
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
