/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.rulerepository;

import com.tibco.sonar.plugins.bw.check.AbstractCheck;
import com.tibco.sonar.plugins.bw5.check.sapadapter.SapFileSinkFileCountHardcodingCheck;
import com.tibco.sonar.plugins.bw5.check.sapadapter.SapFileSinkFileLimitHardcodingCheck;
import com.tibco.sonar.plugins.bw5.check.sapadapter.SapRPCClientSubjectHardcodingCheck;
import com.tibco.sonar.plugins.bw5.check.sharedhttp.HardCodedHostCheck;
import com.tibco.sonar.plugins.bw5.check.sharedhttp.HardCodedPortCheck;
import com.tibco.sonar.plugins.bw5.check.sharedjdbc.HardCodedPasswordCheck;
import com.tibco.sonar.plugins.bw5.check.sharedjdbc.HardCodedUrlCheck;
import com.tibco.sonar.plugins.bw5.check.sharedjdbc.HardCodedUserCheck;
import com.tibco.sonar.plugins.bw5.check.sharedjms.HardCodedJndiPasswordCheck;
import com.tibco.sonar.plugins.bw5.check.sharedjms.HardCodedJndiUrlCheck;
import com.tibco.sonar.plugins.bw5.check.sharedjms.HardCodedJndiUserCheck;
import com.tibco.sonar.plugins.bw5.language.BusinessWorks5Language;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;



/**
 * Replacement for org.sonar.plugins.squid.SquidRuleRepository
 */
public class ProcessRuleDefinition implements RulesDefinition {

    public static final String REPOSITORY_KEY = BusinessWorks5Language.KEY;
    protected static final String REPOSITORY_NAME = "SonarQube";
    protected static final List<String> LANGUAGE_KEYS = Arrays.asList(BusinessWorks5Language.KEY);


    public ProcessRuleDefinition() {
    }

    private void defineRulesForLanguage(Context context, String repositoryKey, String repositoryName, String languageKey) {
        NewRepository repository = context.createRepository(repositoryKey, languageKey).setName(repositoryName);
        RulesDefinitionAnnotationLoader annotationLoader = new RulesDefinitionAnnotationLoader();
        annotationLoader.load(repository, getChecks().toArray(new Class[]{}));

        for (NewRule rule : repository.rules()) {
            setDescriptionFromHtml(rule);
        }

        NewRule templateRule = repository.rule("XPathCheck");
        if(templateRule != null){

            templateRule.setTemplate(true);        
        }

        repository.done();
    }

    private void setDescriptionFromHtml(NewRule rule) {
        String htmlPath = "/org/sonar/l10n/bw5/rules/" + rule.key() + ".html";
        String description = "<p></p>";
        try {
            description = new BufferedReader(
                    new InputStreamReader(this.getClass().getResourceAsStream(htmlPath), StandardCharsets.UTF_8))
                    .lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            description= "<p>Description not available</p>";
        }
        rule.setHtmlDescription(description);
    }

    @Override
    public void define(Context context) {
        LANGUAGE_KEYS.forEach(languageKey -> defineRulesForLanguage(context, ProcessRuleDefinition.REPOSITORY_KEY, ProcessRuleDefinition.REPOSITORY_NAME, languageKey));
    }

    public static List<Class<?>> getChecks() {
        return Arrays.asList(new Class[]{
            //Custom
            com.tibco.sonar.plugins.bw5.check.process.DeadProcessCheckForStarterProcess.class,
            com.tibco.sonar.plugins.bw5.check.process.DeadProcessCheckForSubProcess.class,
            com.tibco.sonar.plugins.bw5.check.process.DeadProcessCheckForDynamicSubProcess.class,
            com.tibco.sonar.plugins.bw5.check.process.ApplicationJsonRuleCheck.class,
            // Common
            com.tibco.sonar.plugins.bw5.check.process.NoDescriptionCheck.class,
            // Catch activities
            com.tibco.sonar.plugins.bw5.check.activity.catcherror.CustomCatchCheck.class,
            com.tibco.sonar.plugins.bw5.check.activity.catcherror.CatchAllCheck.class,
            // Generic Reusable Rule for Hard Coded Values
            com.tibco.sonar.plugins.bw5.check.activity.CustomHardCodedCheck.class,
            // HTTP Request Activity
            com.tibco.sonar.plugins.bw5.check.activity.http.request.HardCodedHostCheck.class,
            com.tibco.sonar.plugins.bw5.check.activity.http.request.HardCodedPortCheck.class,
            com.tibco.sonar.plugins.bw5.check.activity.http.request.HardCodedTimeoutCheck.class,
            com.tibco.sonar.plugins.bw5.check.activity.http.request.HardCodedUriCheck.class,
            // HTTP Receiver Activity
            com.tibco.sonar.plugins.bw5.check.activity.jms.queue.receiver.HardCodedDestinationCheck.class,
            com.tibco.sonar.plugins.bw5.check.activity.jms.queue.receiver.HardCodedMaxSessionsCheck.class,
            // JMS Queue Request Reply Activity
            com.tibco.sonar.plugins.bw5.check.activity.jms.queue.requestor.HardCodedDestinationCheck.class,
            com.tibco.sonar.plugins.bw5.check.activity.jms.queue.requestor.HardCodedTimeoutCheck.class,
            // JMS Queue Sender Activity
            com.tibco.sonar.plugins.bw5.check.activity.jms.queue.sender.HardCodedDestinationCheck.class,
            // JMS Topic Publisher Activity
            com.tibco.sonar.plugins.bw5.check.activity.jms.topic.publisher.HardCodedDestinationCheck.class,
            // JMS Topic Subscriber
            com.tibco.sonar.plugins.bw5.check.activity.jms.topic.subscriber.HardCodedDestinationCheck.class,
            // SOAP Receiver Activity
            com.tibco.sonar.plugins.bw5.check.activity.soap.receiver.HardCodedSoapActionCheck.class,
            // SOAP Request Reply Activity
            com.tibco.sonar.plugins.bw5.check.activity.soap.request.HardCodedSoapActionCheck.class,
            com.tibco.sonar.plugins.bw5.check.activity.soap.request.HardCodedTimeoutCheck.class,
            com.tibco.sonar.plugins.bw5.check.activity.soap.request.HardCodedUrlCheck.class,
            SapFileSinkFileCountHardcodingCheck.class,
            SapFileSinkFileLimitHardcodingCheck.class,
            SapRPCClientSubjectHardcodingCheck.class, HardCodedPortCheck.class,
            HardCodedHostCheck.class, HardCodedUrlCheck.class,
            HardCodedPasswordCheck.class,
            HardCodedUserCheck.class, HardCodedJndiUrlCheck.class,
            HardCodedJndiPasswordCheck.class,
            HardCodedJndiUserCheck.class,
            com.tibco.sonar.plugins.bw5.check.XPathCheck.class,
            com.tibco.sonar.plugins.bw5.check.sharedjms.HardCodedPasswordCheck.class,
            com.tibco.sonar.plugins.bw5.check.sharedjms.HardCodedUserCheck.class,
            com.tibco.sonar.plugins.bw5.check.sharedjms.HardCodedUrlCheck.class});
    }

    public static List<AbstractCheck> getCheckList() {
        return Arrays.asList(//Custom
                new com.tibco.sonar.plugins.bw5.check.process.DeadProcessCheckForStarterProcess(),
                new com.tibco.sonar.plugins.bw5.check.process.DeadProcessCheckForSubProcess(),
                new com.tibco.sonar.plugins.bw5.check.process.DeadProcessCheckForDynamicSubProcess(),
                new com.tibco.sonar.plugins.bw5.check.process.ApplicationJsonRuleCheck(),
                // Common
                new com.tibco.sonar.plugins.bw5.check.process.NoDescriptionCheck(),
                // Catch activities
                new com.tibco.sonar.plugins.bw5.check.activity.catcherror.CustomCatchCheck(),
                new com.tibco.sonar.plugins.bw5.check.activity.catcherror.CatchAllCheck(),
                // Generic Reusable Rule for Hard Coded Values
                new com.tibco.sonar.plugins.bw5.check.activity.CustomHardCodedCheck(),
                // HTTP Request Activity
                new com.tibco.sonar.plugins.bw5.check.activity.http.request.HardCodedHostCheck(),
                new com.tibco.sonar.plugins.bw5.check.activity.http.request.HardCodedPortCheck(),
                new com.tibco.sonar.plugins.bw5.check.activity.http.request.HardCodedTimeoutCheck(),
                new com.tibco.sonar.plugins.bw5.check.activity.http.request.HardCodedUriCheck(),
                // HTTP Receiver Activity
                new com.tibco.sonar.plugins.bw5.check.activity.jms.queue.receiver.HardCodedDestinationCheck(),
                new com.tibco.sonar.plugins.bw5.check.activity.jms.queue.receiver.HardCodedMaxSessionsCheck(),
                // JMS Queue Request Reply Activity
                new com.tibco.sonar.plugins.bw5.check.activity.jms.queue.requestor.HardCodedDestinationCheck(),
                new com.tibco.sonar.plugins.bw5.check.activity.jms.queue.requestor.HardCodedTimeoutCheck(),
                // JMS Queue Sender Activity
                new com.tibco.sonar.plugins.bw5.check.activity.jms.queue.sender.HardCodedDestinationCheck(),
                // JMS Topic Publisher Activity
                new com.tibco.sonar.plugins.bw5.check.activity.jms.topic.publisher.HardCodedDestinationCheck(),
                // JMS Topic Subscriber
                new com.tibco.sonar.plugins.bw5.check.activity.jms.topic.subscriber.HardCodedDestinationCheck(),
                // SOAP Receiver Activity
                new com.tibco.sonar.plugins.bw5.check.activity.soap.receiver.HardCodedSoapActionCheck(),
                // SOAP Request Reply Activity
                new com.tibco.sonar.plugins.bw5.check.activity.soap.request.HardCodedSoapActionCheck(),
                new com.tibco.sonar.plugins.bw5.check.activity.soap.request.HardCodedTimeoutCheck(),
                new com.tibco.sonar.plugins.bw5.check.activity.soap.request.HardCodedUrlCheck(),
                new SapFileSinkFileCountHardcodingCheck(),
                new SapFileSinkFileLimitHardcodingCheck(),
                new SapRPCClientSubjectHardcodingCheck(),
                new  HardCodedPortCheck(),
                new HardCodedHostCheck(),
                new  HardCodedUrlCheck(),
                new HardCodedPasswordCheck(),
                new HardCodedUserCheck(),
                new HardCodedJndiUrlCheck(),
                new HardCodedJndiPasswordCheck(),
                new HardCodedJndiUserCheck(),
                new com.tibco.sonar.plugins.bw5.check.sharedjms.HardCodedPasswordCheck(),
                new com.tibco.sonar.plugins.bw5.check.sharedjms.HardCodedUserCheck(),
                new com.tibco.sonar.plugins.bw5.check.sharedjms.HardCodedUrlCheck(),
                new com.tibco.sonar.plugins.bw5.check.XPathCheck());
    }
}
