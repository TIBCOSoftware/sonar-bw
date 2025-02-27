/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.check.sapadapter;

import com.tibco.utils.common.helper.XmlHelper;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.sonar.plugins.bw5.check.AbstractXmlCheck;
import com.tibco.sonar.plugins.bw5.check.CheckConstants;
import com.tibco.sonar.plugins.bw5.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw5.source.XmlBw5Source;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;

@Rule(key = SapRPCClientSubjectHardcodingCheck.RULE_KEY, name = CheckConstants.RULE_SAPADAPTER_SAPRPCCLIENTSUBJECTHARDCODINGCHECK_NAME, description = CheckConstants.RULE_SAPADAPTER_SAPRPCCLIENTSUBJECTHARDCODINGCHECK_DESCRIPTION, priority = Priority.CRITICAL, tags = { "bug" })
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.CRITICAL)
public class SapRPCClientSubjectHardcodingCheck extends AbstractXmlCheck {

    private static final Logger LOG = LoggerFactory.getLogger(SapRPCClientSubjectHardcodingCheck.class);
	public static final String RULE_KEY = "SapRPCClientSubjectHardcodingCheck";
	public static final String CLIENT_ELEMENT_NAME = "//*[local-name()='client']";
	public static final String CLIENT_ELEMENT_NAMESPACE = "http://www.tibco.com/xmlns/aemeta/services/2002";
	public static final String SUBJECT_ELEMENT_NAME = "AEService:subject";
	public static final String SUBJECT_ELEMENT_DESCRIPTION = "Hardcoded subject in RPC Client in Sap Adapter";

	@Override
	protected void validateXml(XmlBw5Source xmlSource) {
		if("adr3".equals(xmlSource.getExtension())) {
			Document document = xmlSource.getDocument(true);
			try {

				NodeList clientNodeList = XmlHelper.evaluateXpathNodeSet(document.getDocumentElement(), CLIENT_ELEMENT_NAME);

				for (int temp = 0; temp < clientNodeList.getLength(); temp++) {
					Node nNode = clientNodeList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						if (eElement.getAttribute("objectType").equals("endpoint.RVRPCClient")) {
							xmlSource.findAndValidateHardCodedChild(getRuleKey(), eElement, SUBJECT_ELEMENT_NAME,
									SUBJECT_ELEMENT_DESCRIPTION);
						}
					}
				}
			} catch (Exception e) {
				reportIssueOnFile(SUBJECT_ELEMENT_DESCRIPTION);
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
