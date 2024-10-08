/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw5.check.sharedjdbc;

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

@Rule(key = HardCodedUserCheck.RULE_KEY, name = CheckConstants.RULE_SHAREDJDBC_SHAREDJDBCHARDCODEDUSER_NAME, description =  CheckConstants.RULE_SHAREDJDBC_SHAREDJDBCHARDCODEDUSER_DESCRIPTION, priority = Priority.MAJOR)
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class HardCodedUserCheck extends AbstractXmlCheck {

    private static final Logger LOG = Loggers.get(HardCodedUserCheck.class);
	public static final String RULE_KEY = "SharedJdbcHardCodedUser";
	public static final String CONFIG_ELEMENT_NAME = "config";
	public static final String USER_ELEMENT_NAME = "user";
	public static final String USER_ELEMENT_DESC = "Shared JDBC connection resource user";

	@Override
	protected void validateXml(XmlBw5Source xmlSource) {
		Document document = xmlSource.getDocument(false);
		try{
			Element config = XmlHelper.firstChildElement(document.getDocumentElement(), null, CONFIG_ELEMENT_NAME);
			if(config.hasChildNodes()){
				xmlSource.findAndValidateHardCodedChild(getRuleKey(), config, USER_ELEMENT_NAME, USER_ELEMENT_DESC);
			}else{				
                                reportIssueOnFile("Shared JDBC connection resource configuration is empty", xmlSource.getLineForNode(config));
			}
		}catch (Exception e) {			
                        reportIssueOnFile("No configuration found in shared JDBC connection resource");
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
