package com.tibco.sonar.plugins.bw6.check.process;

import java.util.Iterator;
import java.util.List;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.ProcessSonarWayProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.sonar.plugins.bw6.violation.DefaultViolation;
import com.tibco.sonar.plugins.bw6.violation.Violation;
import com.tibco.utils.bw.model.Activity;
import com.tibco.utils.bw.model.Process;

@Rule(key = ChoiceOtherwiseCheck.RULE_KEY, name="Choice Condition with No Otherwise Check", priority = Priority.MAJOR, description = "This rule checks all activities input mapping for choice statement. As a coding best practice, the choice statement should always include the option otherwise.")
@BelongsToProfile(title = ProcessSonarWayProfile.defaultProfileName, priority = Priority.MAJOR)
public class ChoiceOtherwiseCheck extends AbstractProcessCheck {
	public static final String RULE_KEY = "ChoiceWithNoOtherwise";

	@Override
	protected void validate(ProcessSource processSource) {
		Process process = processSource.getProcessModel();
		List<Activity> activities = process.getActivities();
		for (Iterator<Activity> iterator = activities.iterator(); iterator.hasNext();) {
			Activity activity =  iterator.next();
			String expr = activity.getExpression();
			if(expr != null){
				if(expr.contains("xsl:choose") && !expr.contains("xsl:otherwise")){
                                        reportIssueOnFile("The choice statement in activity input of "+activity.getName()+" does not include the option otherwise");
				}
			}
		}
	}
}
