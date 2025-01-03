/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw.violations;

import org.sonar.api.rules.Rule;

/**
 * Checks and analyzes report measurements, violations and other findings in
 * WebSourceCode.
 * 
 * @author TIBCODX Toolkit
 */
public class DefaultViolation implements Violation {

	private final Rule rule;
	private final int line;
	private final String message;

	public DefaultViolation(Rule rule, int line, String message) {
		this.rule = rule;
		this.line = line;
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see com.tibco.sonar.plugins.bw.issue.Issue#getRule()
	 */
	public Rule getRule() {
		return rule;
	}

	/* (non-Javadoc)
	 * @see com.tibco.sonar.plugins.bw.issue.Issue#getLine()
	 */
	public int getLine() {
		return line;
	}

	/* (non-Javadoc)
	 * @see com.tibco.sonar.plugins.bw.issue.Issue#getMessage()
	 */
	public String getMessage() {
		return message;
	}
}
