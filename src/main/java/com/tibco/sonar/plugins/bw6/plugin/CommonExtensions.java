/*
 * Copyright (C) 2016-2023. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.plugin;

//import com.tibco.sonar.plugins.bw.colorizers.BusinessWorksProcessColorizerFormat;
import com.tibco.sonar.plugins.bw6.metric.BusinessWorksMetrics;
import com.tibco.sonar.plugins.bw6.widget.BusinessWorksMetricsWidget;
import java.util.ArrayList;

import java.util.List;

public final class CommonExtensions {

	private CommonExtensions() {
	}

	@SuppressWarnings("rawtypes")
	public static List getExtensions() {
		List builder = new ArrayList();
		builder.add(BusinessWorksMetrics.class);
		builder.add(BusinessWorksMetricsWidget.class);
		//builder.add(BusinessWorksProcessColorizerFormat.class);
		return builder;
	}

}
