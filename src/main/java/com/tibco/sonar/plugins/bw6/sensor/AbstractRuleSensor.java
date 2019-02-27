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
package com.tibco.sonar.plugins.bw6.sensor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jfree.util.Log;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
//import org.sonar.api.checks.AnnotationCheckFactory;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
//import org.sonar.api.issue.Issuable;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.batch.fs.InputModule;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import com.google.common.annotations.VisibleForTesting;
import com.tibco.sonar.plugins.bw6.check.*;
import com.tibco.sonar.plugins.bw6.check.process.*;
import com.tibco.sonar.plugins.bw6.source.Source;
import com.tibco.sonar.plugins.bw6.source.XmlSource;
import com.tibco.sonar.plugins.bw6.violation.Violation;

/**
 * XmlSensor provides analysis of xml files.
 * 
 * @author Kapil Shivarkar
 */
public abstract class AbstractRuleSensor extends AbstractSensor {

	//protected AnnotationCheckFactory annotationCheckFactory;
	protected Collection<AbstractCheck> abstractCheck;
	protected FileSystem fs;
	protected Checks checks;
	protected RulesProfile profile;
	private static final Logger LOG = Loggers.get(AbstractRuleSensor.class);

	@SuppressWarnings("rawtypes")
	protected AbstractRuleSensor(RulesProfile profile,
			FileSystem fileSystem, String repositoryKey,
			String languageKey, CheckFactory checkFactory) {
		super(fileSystem, languageKey, checkFactory);
		this.fs = fileSystem;
		this.profile = profile;
		/*this.annotationCheckFactory = AnnotationCheckFactory.create(profile,
				repositoryKey, list);*/
		checks = checkFactory.create(repositoryKey);
		List<Class> allChecks = new ArrayList<Class>();
		allChecks.add(NoDescriptionCheck.class);
		allChecks.add(NumberofActivitiesCheck.class);
		allChecks.add(TransitionLabelCheck.class);
		allChecks.add(ChoiceOtherwiseCheck.class);
		allChecks.add(JDBCWildCardCheck.class);
		allChecks.add(JDBCHardCodeCheck.class);
		allChecks.add(MultipleTransitionCheck.class);
		allChecks.add(DeadLockCheck.class);
		allChecks.add(LogOnlyInSubprocessCheck.class);
		allChecks.add(JMSHardCodeCheck.class);
		allChecks.add(ForEachMappingCheck.class);
		allChecks.add(ForEachGroupCheck.class);
		allChecks.add(NumberofServicesCheck.class);
		allChecks.add(CheckpointAfterRESTCheck.class);
		allChecks.add(CheckpointAfterJDBCÇheck.class);
		allChecks.add(CheckpointAfterHttpCheck.class);
		allChecks.add(CheckpointInTransation.class);
		allChecks.add(JMSAcknowledgementModeCheck.class);
		allChecks.add(CriticalSectionCheck.class);
		allChecks.add(SubProcessInlineCheck.class);
		allChecks.add(ExceptionHandlingCheck.class);
		checks.addAnnotatedChecks(allChecks);
	}

	/**
	 * Analyze the XML files.
	 */
	@SuppressWarnings("unchecked")
	public void analyse(InputModule project, SensorContext sensorContext) {
		this.abstractCheck = checks.all();
		//this.abstractCheck = annotationCheckFactory.getChecks();
		this.project = project;
		super.analyse(project, sensorContext);
	}

	@SuppressWarnings("unchecked")
	protected void analyseFile(java.io.File file) {

		//File resource = File.fromIOFile(file, project);
		InputFile resource = fs.inputFile(fs.predicates().is(file));
		Source sourceCode = new XmlSource(file);
		// Do not execute any XML rule when an XML file is corrupted
		// (SONARXML-13)
		if (sourceCode.parseSource(fileSystem.encoding())) {
			for (AbstractCheck check : abstractCheck) {
				/*check.setRule(annotationCheckFactory.getActiveRule(check)
						.getRule());*/
				RuleKey ruleKey = checks.ruleKey(check);
				check.setRuleKey(ruleKey);
				check.setRule(profile.getActiveRule(ruleKey.repository(), ruleKey.rule()).getRule());
				check.setRuleKey(ruleKey);
				sourceCode = check.validate(sourceCode);
			}
	//		saveIssues(sourceCode, resource);
		}
	}

	@SuppressWarnings("rawtypes")
	@VisibleForTesting
	protected void saveIssues(Source source, InputFile resource) {
/**		for (Violation issue : source.getViolations()) {
					Issuable issuable = resourcePerspectives.as(Issuable.class,
					resource);
			int lineNumber = 1; 
			if(issue.getLine() != 0)
				lineNumber = issue.getLine();

			issuable.addIssue(issuable.newIssueBuilder()
					.ruleKey(issue.getRule().ruleKey()).line(lineNumber)
					.message(issue.getMessage()).build());
		}**/
	    LOG.warn("SAVE ISSUES CODE MISSING FROM THIS PLUGIN - NEEDS FIXING"); 
	}

//	protected abstract void processMetrics();
}
