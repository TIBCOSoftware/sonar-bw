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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.sonar.plugins.bw6.source.Source;
import com.tibco.utils.bw6.model.Activity;

/**
 * Abtract superclass for checks.
 *
 * @author Kapil Shivarkar
 */
public abstract class AbstractProcessCheck extends AbstractCheck{
	
	private final String SQIGNORE_TAG = "SQIGNORE:";
	
	protected abstract void validate(ProcessSource processSource);

	@Override
	public <S extends Source> void validate(S source) {
		if(source instanceof ProcessSource){
			validate((ProcessSource)source);
		}
	}
	
	/**
	 * Indicates if a rule is disabled for a given activity. Reads the documentation node
	 * of the activity and checks this to see if the activity name is disabled. If there is no
	 * documentation node then the function returns false
	 * 
	 * @param activity the activity to check for being disabled
	 * @return true if the activity is disabled, false if not
	 */
	public final boolean isRuleDisabled(Activity activity) {
        final NodeList childNodes = activity.getNode().getChildNodes();
        for (int i = 0; i < childNodes.getLength(); ++i) {
        	final Node node = childNodes.item(i);
        	if (node.getNodeName().equals("bpws:documentation")) {
                final Node contentsNode = node.getFirstChild();
                final String documentation = contentsNode.getNodeValue();
                return isRuleDisabled(documentation);
        	}
        }
        
        return false;
    }
	
	/**
	 * Indicates if a rule is disabled by checking a config string. The string may contain
	 * a line of the form "SQIGNORE:<rule-name>,<rule-name>...
	 * The rule is disabled if the the current rule name is present in the <rule-name>
	 * list.
	 * 
	 * @param config String containing (optionally) the SQIGNORE config
	 * @return true if the rule is disabled, false if not
	 */
	private final boolean isRuleDisabled(String config) {
		final String configLine = Arrays.asList(config.split("\n"))
										  .stream()
										  .map(str -> str.trim())
										  .filter(str -> str.startsWith(SQIGNORE_TAG))
										  .findFirst()
										  .orElse("");
		
		final String ruleName = getRuleKeyName();
		final boolean found = Arrays.asList(configLine.substring(SQIGNORE_TAG.length()).split(","))
									.stream()
									.map(str -> str.replace("\r", ""))
									.map(str -> str.trim())
									.anyMatch(str -> str.equalsIgnoreCase(ruleName));

		return found;
	}
}
