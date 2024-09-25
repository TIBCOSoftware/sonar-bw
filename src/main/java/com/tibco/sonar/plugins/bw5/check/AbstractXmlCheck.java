/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.check;

import com.tibco.sonar.plugins.bw.check.AbstractCheck;
import com.tibco.sonar.plugins.bw.source.Source;
import com.tibco.sonar.plugins.bw5.source.XmlBw5Source;

/**
 * Abtract superclass for checks.
 *
 * @author Matthijs Galesloot
 */
public abstract class AbstractXmlCheck extends AbstractCheck {

	protected abstract <S extends XmlBw5Source> void validate(S xmlSource);

	public <S extends Source> void validate(S source) {
		if(source instanceof XmlBw5Source){
			validate((XmlBw5Source)source);
		}
	}
}
