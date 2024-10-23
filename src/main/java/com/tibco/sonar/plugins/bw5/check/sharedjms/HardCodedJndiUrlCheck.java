
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

@Rule(key = HardCodedJndiUrlCheck.RULE_KEY, name = CheckConstants.RULE_SHAREDJMS_SHAREDJMSHARDCODEDJNDIURL_NAME, description =  CheckConstants.RULE_SHAREDJMS_SHAREDJMSHARDCODEDJNDIURL_DESCRIPTION, priority = Priority.MAJOR)
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class HardCodedJndiUrlCheck extends AbstractXmlCheck {

    private static final Logger LOG = Loggers.get(HardCodedJndiUrlCheck.class);
	private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory
			.getLogger(HardCodedJndiUrlCheck.class.getName());
	
	public static final String RULE_KEY = "SharedJmsHardCodedJndiUrl";
	public static final String CONFIG_ELEMENT_NAME = "config";
	public static final String NAMING_SECTION_ELEMENT_NAME = "NamingEnvironment";
	public static final String JNDI_FLAG_ELEMENT_NAME = "UseJNDI";
	
	public static final String JNDI_URL_ELEMENT_NAME = "NamingURL";
	public static final String JNDI_URL_ELEMENT_DESC = "Shared JMS connection resource JNDI URL";
	

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
						if (useJNDI.getTextContent() != null && "true".equals(useJNDI.getTextContent())) {
							xmlSource.findAndValidateHardCodedChild(getRuleKey(), namingEnvironment, JNDI_URL_ELEMENT_NAME, JNDI_URL_ELEMENT_DESC);
						}
					} else {

						reportIssueOnFile("Shared JMS connection resource naming environment is empty", xmlSource.getLineForNode(namingEnvironment));
					}
				} else {

					reportIssueOnFile("Shared JMS connection resource configuration is empty", xmlSource.getLineForNode(config));
				}
			} catch (Exception e) {
				LOGGER.info("context", e);
				reportIssueOnFile("No configuration found in shared JMS connection resource");

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
