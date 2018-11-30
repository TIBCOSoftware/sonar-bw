package com.tibco.sonar.plugins.bw6.violation;

import org.sonar.api.rules.Rule;

public interface Violation {

	public abstract Rule getRule();

	public abstract int getLine();

	public abstract String getMessage();

}