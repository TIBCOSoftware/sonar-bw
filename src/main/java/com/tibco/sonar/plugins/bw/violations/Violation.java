/*
 * Copyright (C) 2016-2023. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw.violations;

import org.sonar.api.rules.Rule;

public interface Violation {

	Rule getRule();

	int getLine();

	String getMessage();

}