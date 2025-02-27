/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw.violations;

import org.sonar.api.rules.Rule;

public interface Violation {

	Rule getRule();

	int getLine();

	String getMessage();

}