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
package com.tibco.sonar.plugins.bw6.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw6.helper.XmlHelper;
import com.tibco.utils.bw6.model.Process;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = NoDescriptionCheck.RULE_KEY, name = "No Process Description Check", priority = Priority.MINOR, description = "This rule checks if there is description specified for a process.")
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.MINOR)
public class NoDescriptionCheck extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(NoDescriptionCheck.class);
    public static final String RULE_KEY = "ProcessNoDescription";
    public static final String DESCRIPTION_ELEMENT_NAME = "documentation";
    public static final String DESCRIPTION_ELEMENT_NAMESPACE = "http://docs.oasis-open.org/wsbpel/2.0/process/executable";

    @Override
    protected void validate(ProcessSource processSource) {
        LOG.debug("Start validation for rule: " + RULE_KEY);
        Process process = processSource.getProcessModel();
        Document document = process.getProcessXmlDocument();

        Element description = XmlHelper.firstChildElement(
                document.getDocumentElement(),
                DESCRIPTION_ELEMENT_NAMESPACE, DESCRIPTION_ELEMENT_NAME);
        if (description != null) {
            LOG.debug("Process description for process [" + process.getBasename() + "]: " + description.getTextContent());
            if (description.getTextContent() == null
                    || description.getTextContent().isEmpty()) {
                //TODO Add line here
                reportIssueOnFile("Empty description for this process");
            }
        } else {

            reportIssueOnFile("No description found in this process");
        }
        LOG.debug("Validation ended for rule: " + RULE_KEY);
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
