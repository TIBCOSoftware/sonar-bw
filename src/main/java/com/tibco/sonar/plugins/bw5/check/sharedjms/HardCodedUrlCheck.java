/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.check.sharedjms;

import com.tibco.sonar.plugins.bw5.language.SharedJms;
import com.tibco.utils.common.helper.XmlHelper;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tibco.sonar.plugins.bw5.check.AbstractXmlCheck;
import com.tibco.sonar.plugins.bw5.check.CheckConstants;
import com.tibco.sonar.plugins.bw5.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw5.source.XmlBw5Source;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = HardCodedUrlCheck.RULE_KEY, name = CheckConstants.RULE_SHAREDJMS_SHAREDJMSHARDCODEDURL_NAME, description =  CheckConstants.RULE_SHAREDJMS_SHAREDJMSHARDCODEDURL_DESCRIPTION, priority = Priority.MAJOR)
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class HardCodedUrlCheck extends AbstractXmlCheck {

    private static final Logger LOG = Loggers.get(HardCodedUrlCheck.class);
    public static final String RULE_KEY = "SharedJmsHardCodedUrl";
    public static final String CONFIG_ELEMENT_NAME = "config";

    public static final String NAMING_SECTION_ELEMENT_NAME = "NamingEnvironment";
    public static final String JNDI_FLAG_ELEMENT_NAME = "UseJNDI";

    public static final String URL_ELEMENT_NAME = "ProviderURL";
    public static final String URL_ELEMENT_DESC = "Shared JMS connection resource URL";

    @Override
    protected void validateXml(XmlBw5Source xmlSource) {
        if(SharedJms.KEY.equals(xmlSource.getExtension())) {
            Document document = xmlSource.getDocument(false);
            try {
                Element config = XmlHelper.firstChildElement(document.getDocumentElement(), CONFIG_ELEMENT_NAME);
                if (config.hasChildNodes()) {
                    Element namingEnvironment = XmlHelper.firstChildElement(config, NAMING_SECTION_ELEMENT_NAME);
                    if (config.hasChildNodes()) {
                        Element useJNDI = XmlHelper.firstChildElement(namingEnvironment, JNDI_FLAG_ELEMENT_NAME);
                        if (useJNDI.getTextContent() == null || !useJNDI.getTextContent().equals("true")) {
                            xmlSource.findAndValidateHardCodedChild(getRuleKey(), namingEnvironment, URL_ELEMENT_NAME, URL_ELEMENT_DESC);
                        }
                    } else {
                        reportIssueOnFile("Shared JMS connection resource naming environment is empty", xmlSource.getLineForNode(namingEnvironment));
                    }
                } else {
                    reportIssueOnFile("Shared JMS connection resource configuration is empty", xmlSource.getLineForNode(config));
                }
            } catch (Exception e) {
                reportIssueOnFile("No configuration found in shared JMS connection resource", 1);
            }
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
