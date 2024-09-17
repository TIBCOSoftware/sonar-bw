/*
 * SonarQube Java
 * Copyright (C) 2012 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
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

@Rule(key = HardCodedPasswordCheck.RULE_KEY, name = CheckConstants.RULE_SHAREDJDBC_SHAREDJDBCHARDCODEDPASSWORD_NAME, description = CheckConstants.RULE_SHAREDJDBC_SHAREDJDBCHARDCODEDPASSWORD_DESCRIPTION, priority = Priority.MAJOR)
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class HardCodedPasswordCheck extends AbstractXmlCheck {

    private static final Logger LOG = Loggers.get(HardCodedPasswordCheck.class);
    public static final String RULE_KEY = "SharedJdbcHardCodedPassword";
    public static final String CONFIG_ELEMENT_NAME = "config";
    public static final String PWD_ELEMENT_NAME = "password";
    public static final String PWD_ELEMENT_DESC = "Shared JDBC connection resource password";

    @Override
    protected void validate(XmlBw5Source xmlSource) {
        Document document = xmlSource.getDocument(false);
        try {
            Element config = XmlHelper.firstChildElement(document.getDocumentElement(), CONFIG_ELEMENT_NAME);
            if (config.hasChildNodes()) {
                xmlSource.findAndValidateHardCodedChild(getRuleKey(), config, PWD_ELEMENT_NAME, PWD_ELEMENT_DESC);
            } else {
                reportIssueOnFile("Shared JDBC connection resource configuration is empty", xmlSource.getLineForNode(config));
            }
        } catch (Exception e) {
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
