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
package com.tibco.sonar.plugins.bw5.check.process;

import com.tibco.utils.common.helper.XmlHelper;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tibco.sonar.plugins.bw5.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw5.check.CheckConstants;
import com.tibco.sonar.plugins.bw5.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw5.source.ProcessSource;
import com.tibco.utils.bw5.model.Process;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = NoDescriptionCheck.RULE_KEY, name = CheckConstants.RULE_PROCESS_PROCESSNODESCRIPTION_NAME, description = CheckConstants.RULE_PROCESS_PROCESSNODESCRIPTION_DESCRIPTION, priority = Priority.MAJOR)
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MAJOR)
public class NoDescriptionCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(NoDescriptionCheck.class);
    public static final String RULE_KEY = "ProcessNoDescription";
    public static final String DESCRIPTION_ELEMENT_NAME = "description";
    public static final String DESCRIPTION_ELEMENT_NAMESPACE = "http://xmlns.tibco.com/bw/process/2003";

    @Override
    protected void validate(ProcessSource processSource) {
        Process process = processSource.getProcessModel();
        Document document = process.getProcessXmlDocument();
        try {
            Element description = XmlHelper.firstChildElement(
                    document.getDocumentElement(),
                    DESCRIPTION_ELEMENT_NAMESPACE, DESCRIPTION_ELEMENT_NAME);
            if (description.getTextContent() == null
                    || description.getTextContent().isEmpty()) {
                reportIssueOnFile("Empty description for this process", 1);
            }
        } catch (Exception e) {

            reportIssueOnFile("No description found in this process", 1);
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
