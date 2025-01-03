/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check;

import com.tibco.sonar.plugins.bw.check.AbstractCheck;
import com.tibco.sonar.plugins.bw6.source.SharedResourceSource;
import com.tibco.sonar.plugins.bw.source.Source;

/**
 * Abtract superclass for checks.
 *
 * @author Kapil Shivarkar
 */
public abstract class AbstractResourceCheck extends AbstractCheck {
	
	protected abstract void validate(SharedResourceSource processSource);

	@Override
	public <S extends Source> void validate(S source) {
		if(source instanceof SharedResourceSource){
			validate((SharedResourceSource)source);
		}
	}
}
