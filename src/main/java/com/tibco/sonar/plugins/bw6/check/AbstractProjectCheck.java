/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check;

import com.tibco.sonar.plugins.bw.check.AbstractCheck;
import com.tibco.sonar.plugins.bw6.source.ProjectSource;
import com.tibco.sonar.plugins.bw.source.Source;

/**
 * Abtract superclass for checks.
 *
 * @author Kapil Shivarkar
 */
public abstract class AbstractProjectCheck extends AbstractCheck {
	
	protected abstract void validate(ProjectSource processSource);

	@Override
	public <S extends Source> void validate(S source) {
		if(source instanceof ProjectSource){
			validate((ProjectSource)source);
		}
	}
}
