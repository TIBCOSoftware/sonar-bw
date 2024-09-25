/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.check;

import com.tibco.sonar.plugins.bw.check.AbstractCheck;
import com.tibco.sonar.plugins.bw.source.Source;
import com.tibco.sonar.plugins.bw5.source.ProcessSource;

/**
 * Abtract superclass for checks.
 *
 * @author Matthijs Galesloot
 */
public abstract class AbstractProcessCheck extends AbstractCheck {
	
	protected abstract void validate(ProcessSource processSource);

	public <S extends Source> void validate(S source) {
		if(source instanceof ProcessSource){
			validate((ProcessSource)source);
		}
	}
}
