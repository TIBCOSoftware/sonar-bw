/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.check.adbadapter;

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
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;

@Rule(key = AdbFileSinkFileCountHardcodingCheck.RULE_KEY, name = CheckConstants.RULE_ADBADAPTER_ADBFILESINKFILECOUNTHARDCODINGCHECK_NAME, description = CheckConstants.RULE_ADBADAPTER_ADBFILESINKFILECOUNTHARDCODINGCHECK_DESCRIPTION, priority = Priority.CRITICAL, tags = { "bug" })
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.CRITICAL)
public class AdbFileSinkFileCountHardcodingCheck extends AbstractXmlCheck {

    private static final Logger LOG = LoggerFactory.getLogger(AdbFileSinkFileCountHardcodingCheck.class);
	public static final String RULE_KEY = "AdbFileSinkFileCountHardcodingCheck";
	public static final String FILESINK_ELEMENT_NAME = "//*[local-name()='fileSink']";
	public static final String FILESINK_ELEMENT_NAMESPACE = "http://www.tibco.com/xmlns/aemeta/adapter/2002";
	public static final String FILE_COUNT_ELEMENT_NAME = "AESDK:fileCount";
	public static final String FILE_COUNT_ELEMENT_DESCRIPTION = "Hardcoded File Count in FileSink of Adb Adapter";

	@Override
	protected void validateXml(XmlBw5Source xmlSource) {
		if("adb".equals(xmlSource.getExtension())) {
			Document document = xmlSource.getDocument(true);
			try {
				Element fileSinkElement = XmlHelper.firstChildElement(document.getDocumentElement(),
						FILESINK_ELEMENT_NAMESPACE, FILESINK_ELEMENT_NAME);
				xmlSource.findAndValidateHardCodedChild(getRuleKey(), fileSinkElement, FILE_COUNT_ELEMENT_NAME,
						FILE_COUNT_ELEMENT_DESCRIPTION);
			} catch (Exception e) {
				reportIssueOnFile(FILE_COUNT_ELEMENT_DESCRIPTION);
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
