package com.tibco.sonar.plugins.bw6.check.process;

import java.util.List;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.Node;
import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw.model.Activity;

@Rule(key = ForEachMappingCheck.RULE_KEY, name="For-Each Mapping Check", priority = Priority.INFO, description = "This rule checks the Input mappings of activities. In activity Input mapping for performance reasons, it is recommended ato use Copy-Of instead of For-Each whenever possible.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.INFO)
public class ForEachMappingCheck extends AbstractProcessCheck {
	public static final String RULE_KEY = "ForEachMapping";

	@Override
	protected void validate(ProcessSource processSource) {
		List<Activity> list = processSource.getProcessModel().getActivities();
            for (Activity activity : list) {
                Node node = activity.getNode();
                if(node.getAttributes().getNamedItem("expression") != null && node.getAttributes().getNamedItem("expression").getTextContent().contains("xsl:for-each")){
                    reportIssueOnFile("For-Each is used in the Input mapping of activity "+activity.getName()+". For performance reasons it is recommended to use Copy-Of instead of For-Each in the Input mapping whenever possible. ");
                }
            }
	}

        @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }
}
