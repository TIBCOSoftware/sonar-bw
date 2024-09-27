/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.check.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.sonar.plugins.bw5.check.AbstractXmlCheck;
import com.tibco.sonar.plugins.bw5.check.CheckConstants;
import com.tibco.sonar.plugins.bw5.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw5.source.XmlBw5Source;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = DeadProcessCheckForStarterProcess.RULE_KEY, name = CheckConstants.RULE_PROCESS_DEADPROCESSCHECKFORSTARTERPROCESS_NAME, description =  CheckConstants.RULE_PROCESS_DEADPROCESSCHECKFORSTARTERPROCESS_DESCRIPTION, priority = Priority.CRITICAL, tags = { "bug" })
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.CRITICAL)
public class DeadProcessCheckForStarterProcess extends AbstractXmlCheck {

    private static final Logger LOG = Loggers.get(DeadProcessCheckForStarterProcess.class);
	public static final String RULE_KEY = "DeadProcessCheckForStarterProcess";

	public static final String STARTER_ELEMENT_NAMESPACE = "http://xmlns.tibco.com/bw/process/2003";
	public static final String STARTER_ELEMENT_NAME = "starter";
	public static final String DEAD_CODE_DESCRIPTION = "Dead Code: This Starter process is not used any where in the code";

	@Override
	protected void validate(XmlBw5Source xmlSource) {
		Document document = xmlSource.getDocument(true);
		try {

			NodeList nodeList = document.getDocumentElement().getElementsByTagNameNS(STARTER_ELEMENT_NAMESPACE,
					STARTER_ELEMENT_NAME);
			File sourceDir = new File("SourceCode\\");
			Node nameNode = document.getDocumentElement().getElementsByTagNameNS(STARTER_ELEMENT_NAMESPACE, "name")
					.item(0);
			String name = nameNode.getTextContent();


			if (nodeList.getLength() < 1) {
				// for sub process
			} else {

				boolean isPresent = false;
				// Stater(or Main) process logic
				String[] extensions = new String[] { "archive" };
				List<File> archiveFiles = (List<File>) FileUtils.listFiles(sourceDir, extensions, true);
				for (File archiveFile : archiveFiles) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(archiveFile))) {
                        String sCurrLine = "";
                        while ((sCurrLine = reader.readLine()) != null) {
                            if (sCurrLine.contains(name)) {
                                isPresent = true;
                            }
                        }

                    }
                }

				if (!isPresent) {					
                                        reportIssueOnFile("The " + DEAD_CODE_DESCRIPTION);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception Occured in DeadProcessCheckForStarterProcess");
			//Violation violation = new DefaultViolation(getRuleKey(), 1, DEAD_CODE_DESCRIPTION);
			//xmlSource.addViolation(violation);
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
