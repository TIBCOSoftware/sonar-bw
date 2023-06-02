/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.rulerepository;

import com.google.common.io.Resources;
import com.tibco.sonar.plugins.bw6.check.AbstractCheck;
import java.util.Arrays;
import java.util.List;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;
import com.tibco.sonar.plugins.bw6.language.BWProcessLanguage;
import java.io.File;
import java.io.IOException;
import static java.nio.charset.StandardCharsets.UTF_8;
import org.apache.commons.io.FileUtils;

public final class ProcessRuleDefinition implements RulesDefinition {

    protected static final String KEY = "process";
    protected static final String NAME = "process";

    public static final String REPOSITORY_KEY = BWProcessLanguage.KEY + KEY;
    protected static final String REPOSITORY_NAME = BWProcessLanguage.KEY + "-" + NAME;
    protected static final List<String> LANGUAGE_KEYS = Arrays.asList(new String[]{BWProcessLanguage.KEY});

    private static List<Class> checkRules;

    private static Class check[] = {
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
        com.tibco.sonar.plugins.bw6.check.process.UnneededGroupCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.NumberofServicesCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.CheckpointAfterHttpCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.CheckpointAfterRESTCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.CheckpointAfterJDBCCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.CheckpointInTransation.class,
        com.tibco.sonar.plugins.bw6.check.process.JMSAcknowledgementModeCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.CriticalSectionCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.SubProcessInlineCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.ExceptionHandlingCheck.class,
        com.tibco.sonar.plugins.bw6.check.resource.SharedResourceNotUsed.class,
        com.tibco.sonar.plugins.bw6.check.resource.SharedResourceUsingModuleProperty.class,
        com.tibco.sonar.plugins.bw6.check.resource.HttpClientSSLShouldHaveConfidentiality.class,
        com.tibco.sonar.plugins.bw6.check.resource.HttpConnectorShouldHaveConfidentiality.class,
        com.tibco.sonar.plugins.bw6.check.resource.JMSConnectorShouldHaveConfidentiality.class,
        com.tibco.sonar.plugins.bw6.check.resource.SSLServerConnectorShouldHaveTLSprotocol.class,
        com.tibco.sonar.plugins.bw6.check.resource.SSLClientConnectorShouldHaveTLSprotocol.class,
        com.tibco.sonar.plugins.bw6.check.process.OnlyOneOtherwiseConditionCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.NoOtherwiseConditionCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.DefaultTargetNamespaceCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.LastActivityAndEndActivityCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.ParseXMLFromRenderCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.RenderXMLPrettyPrintCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.RenderXMLBinaryCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.ParseXMLBinaryCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.ParseXMLRenderXMLCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.ProcessWithoutTestCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.JDBCTransactionParallelFlowCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.HttpClientMustBeUsedinHTTPBindingCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.ThreadpoolUsageInJDBCActivitiesCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.ListFileActivityToCheckFileExistenceCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.GetFragmentBinaryCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.UnneededEmptyActivityCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.SFTPPutBinaryCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.JMSRequestReplyNonPersistentCheck.class,
        com.tibco.sonar.plugins.bw6.check.project.NumberOfPropertiesSameGroupCheck.class,
        com.tibco.sonar.plugins.bw6.check.project.XMLResourceSameTargetNamespaceCheck.class,
        com.tibco.sonar.plugins.bw6.check.project.EndpointURIFromHTTPBindingSetUsingPropertyCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.ProcessNamingConventionCheck.class,
        com.tibco.sonar.plugins.bw6.check.process.JMSReceiverPlusConfirmCheck.class,
        com.tibco.sonar.plugins.bw6.check.XPathCheck.class,
        com.tibco.sonar.plugins.bw6.check.project.AtLeastOneStarterCheck.class,
        com.tibco.sonar.plugins.bw6.check.project.BWVersionCheck.class,
        com.tibco.sonar.plugins.bw6.check.project.IsMavenProjectCheck.class,
        com.tibco.sonar.plugins.bw6.check.project.OnlyOneKeystoreApplicationModuleCheck.class,
        com.tibco.sonar.plugins.bw6.check.project.PomXmlVersionsHarcodedCheck.class,
        com.tibco.sonar.plugins.bw6.check.project.ProjectStructureCheck.class,
        com.tibco.sonar.plugins.bw6.check.project.SwaggerValidationCheck.class,
     //TODO Disabling it until dependencies are managed com.tibco.sonar.plugins.bw6.check.project.XSDValidationCheck.class, 
        com.tibco.sonar.plugins.bw6.check.project.BindingShouldHavePolicyAssociatedCheck.class,
        com.tibco.sonar.plugins.bw6.check.project.BindingShouldNotHaveHTTPBasicPolicyAssociatedCheck.class,
        com.tibco.sonar.plugins.bw6.check.project.JKSValidationCheck.class
    };

    private static AbstractCheck checkList[] = {
        new com.tibco.sonar.plugins.bw6.check.process.NoDescriptionCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.NumberofActivitiesCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.TransitionLabelCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.ChoiceOtherwiseCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.JDBCWildCardCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.JDBCHardCodeCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.MultipleTransitionCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.DeadLockCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.LogOnlyInSubprocessCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.JMSHardCodeCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.ForEachMappingCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.UnneededGroupCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.NumberofServicesCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.CheckpointAfterHttpCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.CheckpointAfterRESTCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.CheckpointAfterJDBCCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.CheckpointInTransation(),
        new com.tibco.sonar.plugins.bw6.check.process.JMSAcknowledgementModeCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.CriticalSectionCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.SubProcessInlineCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.ExceptionHandlingCheck(),
        new com.tibco.sonar.plugins.bw6.check.resource.SharedResourceNotUsed(),
        new com.tibco.sonar.plugins.bw6.check.resource.SharedResourceUsingModuleProperty(),
        new com.tibco.sonar.plugins.bw6.check.resource.HttpClientSSLShouldHaveConfidentiality(),
        new com.tibco.sonar.plugins.bw6.check.resource.HttpConnectorShouldHaveConfidentiality(),
        new com.tibco.sonar.plugins.bw6.check.resource.JMSConnectorShouldHaveConfidentiality(),
        new com.tibco.sonar.plugins.bw6.check.resource.SSLClientConnectorShouldHaveTLSprotocol(),
        new com.tibco.sonar.plugins.bw6.check.resource.SSLServerConnectorShouldHaveTLSprotocol(),
        new com.tibco.sonar.plugins.bw6.check.process.OnlyOneOtherwiseConditionCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.NoOtherwiseConditionCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.DefaultTargetNamespaceCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.GetFragmentBinaryCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.ProcessWithoutTestCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.JDBCTransactionParallelFlowCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.HttpClientMustBeUsedinHTTPBindingCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.ThreadpoolUsageInJDBCActivitiesCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.LastActivityAndEndActivityCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.ParseXMLFromRenderCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.ParseXMLBinaryCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.ListFileActivityToCheckFileExistenceCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.UnneededEmptyActivityCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.RenderXMLPrettyPrintCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.RenderXMLBinaryCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.ProcessNamingConventionCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.ParseXMLRenderXMLCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.JMSReceiverPlusConfirmCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.JMSRequestReplyNonPersistentCheck(),
        new com.tibco.sonar.plugins.bw6.check.project.NumberOfPropertiesSameGroupCheck(),
        new com.tibco.sonar.plugins.bw6.check.process.SFTPPutBinaryCheck(),
        new com.tibco.sonar.plugins.bw6.check.project.XMLResourceSameTargetNamespaceCheck(),
        new com.tibco.sonar.plugins.bw6.check.project.EndpointURIFromHTTPBindingSetUsingPropertyCheck(),
        new com.tibco.sonar.plugins.bw6.check.XPathCheck(),
        new com.tibco.sonar.plugins.bw6.check.project.AtLeastOneStarterCheck(),
        new com.tibco.sonar.plugins.bw6.check.project.BWVersionCheck(),
        new com.tibco.sonar.plugins.bw6.check.project.IsMavenProjectCheck(),
        new com.tibco.sonar.plugins.bw6.check.project.OnlyOneKeystoreApplicationModuleCheck(),
        new com.tibco.sonar.plugins.bw6.check.project.PomXmlVersionsHarcodedCheck(),
        new com.tibco.sonar.plugins.bw6.check.project.ProjectStructureCheck(),
        new com.tibco.sonar.plugins.bw6.check.project.SwaggerValidationCheck(),
//TODO Disabling it until dependencies are managed         new com.tibco.sonar.plugins.bw6.check.project.XSDValidationCheck(),
        new com.tibco.sonar.plugins.bw6.check.project.BindingShouldHavePolicyAssociatedCheck(),
        new com.tibco.sonar.plugins.bw6.check.project.BindingShouldNotHaveHTTPBasicPolicyAssociatedCheck(),
        new com.tibco.sonar.plugins.bw6.check.project.JKSValidationCheck()
    };

    protected String rulesDefinitionFilePath() {
        return "/rules.xml";
    }

    public ProcessRuleDefinition() {
    }

    private void setDescriptionFromHtml(NewRule rule) {
        String htmlPath = "/org/sonar/l10n/bw6/rules/bw6process/" + rule.key() + ".html";
        String description = "<p></p>";
        try {

            description = Resources.toString(this.getClass().getResource(htmlPath),UTF_8);            
        } catch (Exception e) {
            description= "<p>Description not available</p>";
        }
        rule.setHtmlDescription(description);
    }

    private void defineRulesForLanguage(Context context, String repositoryKey, String repositoryName, String languageKey) {
        NewRepository repository = context.createRepository(repositoryKey, languageKey).setName(repositoryName);
        RulesDefinitionAnnotationLoader annotationLoader = new RulesDefinitionAnnotationLoader();
        annotationLoader.load(repository, check);

        for (NewRule rule : repository.rules()) {
            setDescriptionFromHtml(rule);
        }

        NewRule templateRule = repository.rule("XPathCheck");
        if (templateRule != null) {

            templateRule.setTemplate(true);
        }
        repository.done();

    }

    public static String getRepositoryKeyForLanguage(String languageKey) {
        return REPOSITORY_KEY;
    }

    public static String getRepositoryNameForLanguage(String languageName) {
        return REPOSITORY_NAME;
    }

    @Override
    public void define(Context context) {
        for (String languageKey : LANGUAGE_KEYS) {
            defineRulesForLanguage(context, ProcessRuleDefinition.REPOSITORY_KEY, ProcessRuleDefinition.REPOSITORY_NAME,
                    languageKey);
        }
    }

    public static List<Class> getChecks() {
        checkRules = Arrays.asList(check);
        return checkRules;
    }

    public static List<AbstractCheck> getCheckList() {
        return Arrays.asList(checkList);
    }

}
