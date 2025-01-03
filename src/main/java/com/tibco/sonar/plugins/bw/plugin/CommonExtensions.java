/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw.plugin;

import com.tibco.sonar.plugins.bw6.metric.BusinessWorksMetrics;
import java.util.ArrayList;

import java.util.List;

public final class CommonExtensions {

	private CommonExtensions() {
	}

	@SuppressWarnings("rawtypes")
	public static List getExtensions() {
		List builder = new ArrayList();
		builder.add(BusinessWorksMetrics.class);
		return builder;
	}

}
