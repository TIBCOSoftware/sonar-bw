/*
 * Copyright (C) 2016-2023. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.check.process;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Group;
import com.tibco.utils.bw6.model.Process;
import java.util.Arrays;

import java.util.List;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(key = "CriticalSection", name = "Activities in Critical Section Check", priority = Priority.INFO, description = "Critical section groups cause multiple concurrently running process instances to wait for one process instance to execute the activities in the group. As a result, there may be performance implications when using these groups. This rules checks that the Critical Section group does not include any activities that wait for incoming events or have long durations, such as Request/Reply activities, Wait For (Signal-In) activities, Sleep activity, or other activities that require a long time to execute.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.INFO)
public class CriticalSectionCheck
        extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(CriticalSectionCheck.class);
    public static final String RULE_KEY = "CriticalSection";
    protected static final List<String> CONSTANTS = Arrays.asList("bw.http.waitForHTTPRequest", "bw.file.wait", "bw.generalactivities.sleep", "bw.jms.signalin", "bw.rv.waitforRVMessage", "bw.tcp.waitfortcp", "bw.http.sendHTTPRequest", "bw.ftl.requestreply", "bw.jms.requestreply", "bw.rv.sendRVRequest","bw.generalactivities.sleep");

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        final Process process = processSource.getProcessModel();
        final List<Group> groups = process.getGroups();
        for (final Group group : groups) {
            
            if (group.getType().equals("critical")) {
                group.getActivities().stream().filter((activity) -> (activity.getType() != null && CriticalSectionCheck.CONSTANTS.contains(activity.getType()))).forEachOrdered((Activity activity) -> {
                    reportIssueOnFile("The activity " + activity.getName() + " in process " + process.getBasename() + " should not be used within Critical Section group.",XmlHelper.getLineNumber(activity.getNode()));
                });
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
