/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.tibco.sonar.plugins.bw5.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw5.check.CheckConstants;
import com.tibco.sonar.plugins.bw5.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw5.source.ProcessSource;
import com.tibco.utils.bw5.model.Process;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = ApplicationJsonRuleCheck.RULE_KEY, name = CheckConstants.RULE_PROCESS_APPLICATIONJSONCHECK_NAME, description =  CheckConstants.RULE_PROCESS_APPLICATIONJSONCHECK_DESCRIPTION, priority = Priority.CRITICAL, tags = {"bug"})
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.CRITICAL)
public class ApplicationJsonRuleCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(ApplicationJsonRuleCheck.class);
    public static final String RULE_KEY = "ApplicationJsonCheck";
    public static final String DESCRIPTION_ELEMENT_NAME = "value-of";
    public static final String DESCRIPTION_ELEMENT_NAMESPACE = "http://www.w3.org/1999/XSL/Transform";

    @Override
    protected void validate(ProcessSource processSource) {
        Process process = processSource.getProcessModel();
        Document document = process.getProcessXmlDocument();
        try {
            NodeList nodeList = document.getDocumentElement().getElementsByTagNameNS(DESCRIPTION_ELEMENT_NAMESPACE,
                    DESCRIPTION_ELEMENT_NAME);
            for (int index = 0; index < nodeList.getLength(); index++) {
                if (nodeList.item(index).getParentNode().getNodeName().equals("type")) {
                    Element typeElement = (Element) nodeList.item(index);
                    if (typeElement.getParentNode().getNodeName().equals("type") && typeElement.getAttribute("select").contains("application-json")) {
                            reportIssueOnFile("'application-json' is not supported. Please replace it with 'application/json'", 1);
                    }
                }
            }
        } catch (Exception e) {

            reportIssueOnFile("application-json is not supported");

        }
    }

    @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }

    @Override
    public Logger getLogger() {
        return LOG;
    }

}
