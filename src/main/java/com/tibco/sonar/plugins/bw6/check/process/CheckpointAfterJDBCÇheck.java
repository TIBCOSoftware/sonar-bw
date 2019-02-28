package com.tibco.sonar.plugins.bw6.check.process;

import java.util.Iterator;
import java.util.Map;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.NodeList;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.ProcessSonarWayProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw.model.Activity;
import com.tibco.utils.bw.model.Process;
import com.tibco.utils.bw.model.Transition;

@Rule(key = CheckpointAfterJDBCÇheck.RULE_KEY, name="Checkpoint after JDBC Query Activity Check", priority = Priority.MAJOR, description = "This rule checks the placement of a Checkpoint activity within a process. Do not place checkpoint after or in a parallel flow of Query activities or idempotent activities. Database operations such as Update, Insert and Delete are considered non-idempotent operations. You should always place a checkpoint immediately after any database insert or update activity to persist the response. However, for queries, there is no need to place checkpoints")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.MAJOR)
public class CheckpointAfterJDBCÇheck extends AbstractProcessCheck {
	public static final String RULE_KEY = "CheckpointProcessJDBC";
	private boolean onlyOneViolation = true;
	@Override
	protected void validate(ProcessSource processSource) {
		Process process = processSource.getProcessModel();
		for (Iterator<Activity> iterator = process.getActivities().iterator(); iterator.hasNext();) {
			Activity activity =  iterator.next();
			if(activity.getType() != null && activity.getType().equals("bw.internal.checkpoint")){
				NodeList nodes = activity.getNode().getChildNodes();
				for(int i=0; i< nodes.getLength();i++){
					if(nodes.item(i).getNodeName().equals("bpws:targets")){
						NodeList transitions_To = nodes.item(i).getChildNodes();
						for (int j = 0; j < transitions_To.getLength(); j++) {
							if(transitions_To.item(j).getNodeName().equals("bpws:target")){
								String transitionName = transitions_To.item(j).getAttributes().getNamedItem("linkName").getTextContent();
								if(process.getTransitions().get(transitionName) == null){
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

	public void findViolation(String from, Process process, ProcessSource processSource){
		Activity activity = process.getActivityByName(from);
		if(activity != null){
			String activityType = activity.getType();
			if(activityType != null){
				if(activityType.contains("bw.jdbc.JDBCQuery")){
					if(onlyOneViolation){
						String proc = process.getName();
						proc = proc.substring(proc.lastIndexOf(".")+1).concat(".bwp");
                                                reportIssueOnFile("The process "+proc+" has a Checkpoint activity placed after a JDBC Query activity.");
						onlyOneViolation = false;
					}
				}else{
					Map<String, Transition> transition123 = process.getTransitions();
					for (String key : transition123.keySet()) {
						int index = key.indexOf("To");
						String toActivity = key.substring(index+2);
						if(toActivity.equals(activity.getName())){
							String fromActivity = key.substring(0, index);
							findViolation(fromActivity, process, processSource);
						}
					}
				}
			}
		}
	}

    
}
