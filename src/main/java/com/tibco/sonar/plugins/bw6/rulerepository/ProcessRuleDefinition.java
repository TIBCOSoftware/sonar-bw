package com.tibco.sonar.plugins.bw6.rulerepository;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;

import com.google.common.collect.ImmutableList;
import com.tibco.sonar.plugins.bw6.language.BWProcessLanguage;

public final class ProcessRuleDefinition implements RulesDefinition{



	protected static final String KEY = "process";
	protected static final String NAME = "process";
  
	public static final String REPOSITORY_KEY = BWProcessLanguage.KEY + "-" + KEY;
	protected static final String REPOSITORY_NAME = BWProcessLanguage.KEY + "-" + NAME;
	protected static final List<String> LANGUAGE_KEYS = ImmutableList.of(BWProcessLanguage.KEY);
	
	public static List<Class> checkRules;
	public static Class check[] = {	
			com.tibco.sonar.plugins.bw6.check.process.NoDescriptionCheck.class,
			com.tibco.sonar.plugins.bw6.check.process.NumberofActivitiesCheck.class,
			com.tibco.sonar.plugins.bw6.check.process.TransitionLabelCheck.class,
			com.tibco.sonar.plugins.bw6.check.process.ChoiceOtherwiseCheck.class,
			com.tibco.sonar.plugins.bw6.check.process.JDBCWildCardCheck.class,
			com.tibco.sonar.plugins.bw6.check.process.JDBCHardCodeCheck.class,
			com.tibco.sonar.plugins.bw6.check.process.MultipleTransitionCheck.class,
			com.tibco.sonar.plugins.bw6.check.process.DeadLockCheck.class,
			com.tibco.sonar.plugins.bw6.check.process.LogOnlyInSubprocessCheck.class,
			com.tibco.sonar.plugins.bw6.check.process.JMSHardCodeCheck.class,
			com.tibco.sonar.plugins.bw6.check.process.ForEachMappingCheck.class,
			com.tibco.sonar.plugins.bw6.check.process.ForEachGroupCheck.class,
			com.tibco.sonar.plugins.bw6.check.process.NumberofServicesCheck.class,
			com.tibco.sonar.plugins.bw6.check.process.CheckpointAfterHttpCheck.class,
			com.tibco.sonar.plugins.bw6.check.process.CheckpointAfterRESTCheck.class,
			com.tibco.sonar.plugins.bw6.check.process.CheckpointAfterJDBCÇheck.class,
			com.tibco.sonar.plugins.bw6.check.process.CheckpointInTransation.class,
			com.tibco.sonar.plugins.bw6.check.process.JMSAcknowledgementModeCheck.class,
			com.tibco.sonar.plugins.bw6.check.process.CriticalSectionCheck.class,
			com.tibco.sonar.plugins.bw6.check.process.SubProcessInlineCheck.class
		};
	
	protected String rulesDefinitionFilePath() {
		return "/rules.xml";
	}

	public ProcessRuleDefinition() {
	}
	private void defineRulesForLanguage(Context context, String repositoryKey, String repositoryName, String languageKey) {
		NewRepository repository = context.createRepository(repositoryKey, languageKey).setName(repositoryName);
		/*InputStream rulesXml = this.getClass().getResourceAsStream(rulesDefinitionFilePath());
		if (rulesXml != null) {
			RulesDefinitionXmlLoader rulesLoader = new RulesDefinitionXmlLoader();
			rulesLoader.load(repository, rulesXml, Charsets.UTF_8.name());
		}*/
		RulesDefinitionAnnotationLoader annotationLoader = new RulesDefinitionAnnotationLoader();
		annotationLoader.load(repository, getChecks());
		repository.done();
	}


	public static String getRepositoryKeyForLanguage(String languageKey) {
		return REPOSITORY_KEY;
	}

	public static String getRepositoryNameForLanguage(String languageName) {
		return REPOSITORY_NAME;
	}

	public void define(Context context) {
		for (String languageKey : LANGUAGE_KEYS) {
			/*defineRulesForLanguage(context, ProcessRuleDefinition.getRepositoryKeyForLanguage(languageKey), ProcessRuleDefinition.getRepositoryNameForLanguage(languageKey),
					languageKey);*/
			defineRulesForLanguage(context, ProcessRuleDefinition.REPOSITORY_KEY, ProcessRuleDefinition.REPOSITORY_NAME,
					languageKey);
		}
	}



	@SuppressWarnings("rawtypes")
	public static Class [] getChecks() {
		checkRules = Arrays.asList(check);
		return check;
	}

	/*@SuppressWarnings("rawtypes")
	public static Class [] getChecks() {
		return new Class[] {
				// Common
				com.tibco.businessworks6.sonar.plugin.check.process.NoDescriptionCheck.class			
				// Generic Reusable Rule for Hard Coded Values
				//com.tibco.businessworks6.sonar.plugin.check.activity.CustomHardCodedCheck.class,
				// HTTP Request Activity
				com.tibco.businessworks6.sonar.plugin.check.activity.http.request.HardCodedHostCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.activity.http.request.HardCodedPortCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.activity.http.request.HardCodedTimeoutCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.activity.http.request.HardCodedUriCheck.class,
			// HTTP Receiver Activity
			com.tibco.businessworks6.sonar.plugin.check.activity.jms.queue.receiver.HardCodedDestinationCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.activity.jms.queue.receiver.HardCodedMaxSessionsCheck.class,
			// JMS Queue Request Reply Activity
			com.tibco.businessworks6.sonar.plugin.check.activity.jms.queue.requestor.HardCodedDestinationCheck.class,
			com.tibco.businessworks6.sonar.plugin.check.activity.jms.queue.requestor.HardCodedTimeoutCheck.class,
			// JMS Queue Sender Activity
			com.tibco.businessworks6.sonar.plugin.check.activity.jms.queue.sender.HardCodedDestinationCheck.class,
			// JMS Topic Publisher Activity
			com.tibco.businessworks6.sonar.plugin.check.activity.jms.topic.publisher.HardCodedDestinationCheck.class,
			// JMS Topic Subscriber
			com.tibco.businessworks6.sonar.plugin.check.activity.jms.topic.subscriber.HardCodedDestinationCheck.class,			
		};
	}*/
}