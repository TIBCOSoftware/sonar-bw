/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check;

import com.tibco.sonar.plugins.bw.check.AbstractCheck;
import com.tibco.sonar.plugins.bw.source.Source;
import com.tibco.sonar.plugins.bw.source.XmlSource;
import com.tibco.utils.common.helper.XmlHelper;
import java.util.Collections;
import org.sonar.api.utils.WildcardPattern;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonarsource.analyzer.commons.xml.XmlFile;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author avazquez
 */
@Rule(key = XPathCheck.RULE_KEY, name = "Custom XPath Check", priority = Priority.MAJOR, description = "Custom XPath Check")
public class XPathCheck extends AbstractCheck {

    @RuleProperty(key = "expression", description = "The XPath query", type = "TEXT")
    private String expression;

    @RuleProperty(key = "filePattern", description = "The files to be validated using Ant-style matching patterns")
    private String filePattern;

    @RuleProperty(
            key = "message",
            description = "The issue message",
            defaultValue = "The XPath expression matches this piece of code")
    private String message;

    private static final Logger LOG = LoggerFactory.getLogger(XPathCheck.class);

    public static final String RULE_KEY = "XPathCheck";

    @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }

    @Override
    public Logger getLogger() {
        return LOG;
    }

    @Override
    public <S extends Source> void validate(S source) {
        if (source instanceof XmlSource) {
            validate((XmlSource) source);
        }
    }

    public void validate(XmlSource source) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        XmlFile file = source.getFile();
        if (!isFileIncluded(file)) {
            LOG.debug("File has not need to execute against the rule");
            return;
        }

        
        LOG.debug("Evaluating against expression ["+expression+"]");
        boolean xPathRequiresNamespaces = expression.contains(":");
        Document document = xPathRequiresNamespaces ? file.getNamespaceAwareDocument() : file.getNamespaceUnawareDocument();
        NodeList nodes = XmlHelper.evaluateXPath(document.getDocumentElement(), expression);

        if (nodes != null) {
            LOG.debug("Nodes returned against expression ["+expression+"]: "+nodes.getLength());
            for (int i = 0; i < nodes.getLength(); i++) {
                reportIssueOnFile(message, XmlHelper.getLineNumber(nodes.item(i)));
            }

        } else {
            LOG.debug("Trying as a boolean expression ["+expression+"]: ");
            boolean result = XmlHelper.evaluateXPathAsBoolean(document.getDocumentElement(), expression);
            if (result) {
                reportIssueOnFile(message, Collections.emptyList());
            }
        }
         LOG.debug("Validation ended for rule: " + RULE_KEY);
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private boolean isFileIncluded(XmlFile file) {
        LOG.debug("Checking file name ["+file.getInputFile().filename()+"] against file pattern ["+filePattern+"]");
        return filePattern == null || WildcardPattern.create(filePattern).match(file.getInputFile().filename());
    }

}
