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

import org.sonar.api.rule.RuleKey;
import org.sonar.api.rules.Rule;

import com.tibco.sonar.plugins.bw6.source.Source;

/**
 * Abtract superclass for checks.
 * 
 * @author Kapil Shivarkar
 */
public abstract class AbstractCheck {

	protected Rule rule;
	protected RuleKey ruleKey;

	public RuleKey getRuleKey() {
		return ruleKey;
	}

	public void setRuleKey(RuleKey ruleKey) {
		this.ruleKey = ruleKey;
	}

	public final void setRule(Rule rule) {
		this.rule = rule;
	}
	
	public Rule getRule(){
		return rule;
	}
	
	public abstract <S extends Source> S validate(S source);

}