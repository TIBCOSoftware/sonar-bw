/*
 * Copyright (C) 2016-2023. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.widget;

import org.sonar.api.web.*;

@UserRole(UserRole.USER)
@WidgetCategory({"BusinessWorks"})
@Description("BusinessWorks 6 Project Statistics")
@WidgetProperties({
		@WidgetProperty(key = "max", type = WidgetPropertyType.INTEGER, defaultValue = "80")
	})
public class BusinessWorksMetricsWidget extends AbstractRubyTemplate implements
		RubyRailsWidget {

	public String getId() {
		return "BusinessWorksProjectMetrics";
	}

	public String getTitle() {
		return "BusinessWorks Project Metrics";
	}

	@Override
	protected String getTemplatePath() {
		return "/com/tibco/businessworks6/sonar/plugin/widget/BusinessWorksMetrics.html.erb";
	}

}
