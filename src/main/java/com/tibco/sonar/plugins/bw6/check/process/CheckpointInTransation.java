/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.process;

import java.util.List;
import java.util.Map;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.NodeList;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.bw6.model.Activity;
import com.tibco.utils.bw6.model.Group;
import com.tibco.utils.bw6.model.Process;
import com.tibco.utils.bw6.model.Transition;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = CheckpointInTransation.RULE_KEY, name = "Checkpoint inside Transaction Group Check", priority = Priority.MAJOR, description = "This rule checks the placement of a Checkpoint activity within a process. Do not place checkpoint within or in parallel to a Transaction Group or a Critical Section Group. Checkpoint activities should be placed at points that are guaranteed to be reached before or after the transaction group is reached.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class CheckpointInTransation extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(CheckpointInTransation.class);
    public static final String RULE_KEY = "CheckpointProcessTransaction";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        boolean runvalidationflag = false;
        List<Group> groups = process.getGroups();
        for (Group group : groups) {
            if (group.getType().equals("localTX")) {
                runvalidationflag = true;
                break;
            }
        }

        if (!groups.isEmpty() && runvalidationflag) {
            for (Activity activity : process.getActivities()) {
                if (activity.getType() != null && activity.getType().equals("bw.internal.checkpoint")) {
                    NodeList nodes = activity.getNode().getChildNodes();
                    for (int i = 0; i < nodes.getLength(); i++) {
                        if (nodes.item(i).getNodeName().equals("bpws:targets")) {
                            NodeList transitionsTo = nodes.item(i).getChildNodes();
                            for (int j = 0; j < transitionsTo.getLength(); j++) {
                                if (transitionsTo.item(j).getNodeName().equals("bpws:target")) {
                                    String transitionName = transitionsTo.item(j).getAttributes().getNamedItem("linkName").getTextContent();
                                    if (process.getTransitions().get(transitionName) == null) {
                                        Map<String, String> groupMapping = process.getSynonymsGroupMapping();
                                        transitionName = groupMapping.get(transitionName);
                                    }
                                    Transition transition = process.getTransitions().get(transitionName);
                                    String from = transition.getFrom();
                                    findViolation(from, process, processSource);
                                }
                            }
                        }
                    }
                }
            }
        }
        LOG.debug("Validation ended for rule: " + RULE_KEY);
    }

    public void findViolation(String from, Process process, ProcessSource processSource) {
        Activity activity = process.getActivityByName(from);
        if (activity != null) {
            String activityType = activity.getType();
            if (activityType != null) {
                Map<String, Transition> transition123 = process.getTransitions();
                transition123.keySet().forEach(key -> {
                    int index = key.indexOf("To");
                    String toActivity = key.substring(index + 2);
                    if (toActivity.equals(activity.getName())) {
                        String fromActivity = key.substring(0, index);
                        findViolation(fromActivity, process, processSource);
                    }
                });
            }
        } else {
            if (process.getEventSourceByName(from) == null && process.getGroupByName(from) != null) {
                    if (process.getGroupByName(from).getType().equals("localTX")) {
                        reportIssueOnFile("The Checkpoint activity in the process [" + process.getBasename() + "] is placed within a Transaction group. Checkpoint should not be placed within or in parallel flow to a transaction.",XmlHelper.getLineNumber(process.getGroupByName(from).getNode()));
                    } else if (process.getGroupByName(from).getType().equals("critical")) {
                        reportIssueOnFile("The Checkpoint activity in the process [" + process.getBasename() + "] is placed within a Critical Section group. Checkpoint should not be placed within a Critical Section group.",XmlHelper.getLineNumber(process.getGroupByName(from).getNode()));
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
