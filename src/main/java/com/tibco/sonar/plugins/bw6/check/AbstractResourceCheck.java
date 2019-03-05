/*
 * SonarQube XML Plugin
 * Copyright (C) 2010 SonarSource
 * dev@sonar.codehaus.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tibco.sonar.plugins.bw6.check;

import com.tibco.sonar.plugins.bw6.source.SharedResourceSource;
import com.tibco.sonar.plugins.bw6.source.Source;

/**
 * Abtract superclass for checks.
 *
 * @author Kapil Shivarkar
 */
public abstract class AbstractResourceCheck extends AbstractCheck{
	
	protected abstract void validate(SharedResourceSource processSource);

	@Override
	public <S extends Source> void validate(S source) {
		if(source instanceof SharedResourceSource){
			validate((SharedResourceSource)source);
		}
	}
}
