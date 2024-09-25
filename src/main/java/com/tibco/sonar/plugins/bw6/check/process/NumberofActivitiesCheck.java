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
import com.tibco.utils.bw6.model.Process;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.RuleProperty;

@Rule(key = NumberofActivitiesCheck.RULE_KEY, name = "Number of Activities Check", priority = Priority.MINOR, description = "This rule checks the number of activities within a process, too many activities reduces the process readability")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class NumberofActivitiesCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(NumberofActivitiesCheck.class);
    public static final String RULE_KEY = "NumberOfActivities";

    @RuleProperty(key = "maxActivities", description = "Threshold of activities to be considered excessive for a single process", defaultValue = "24", type = "INTEGER")
    protected int maxActivities;

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        int activityCount = process.getActivities().size();
        LOG.debug("Number of activities for process: ["+process.getBasename()+"]: "+activityCount);
        if (activityCount > maxActivities) {
            reportIssueOnFile("The process " + process.getBasename() + " has too many activities ["+activityCount+"], this reduces the process readablity");
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

    /**
     * @return the maxActivities
     */
    public int getMaxActivities() {
        return maxActivities;
    }

    /**
     * @param maxActivities the maxActivities to set
     */
    public void setMaxActivities(int maxActivities) {
        this.maxActivities = maxActivities;
    }
}
