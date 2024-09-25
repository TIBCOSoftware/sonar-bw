/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.check.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

@Rule(key = DeadProcessCheckForSubProcess.RULE_KEY, name = CheckConstants.RULE_PROCESS_DEADPROCESSCHECKFORSUBPROCESS_NAME, description =  CheckConstants.RULE_PROCESS_DEADPROCESSCHECKFORSUBPROCESS_DESCRIPTION, priority = Priority.CRITICAL, tags = { "bug" })
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.CRITICAL)
public class DeadProcessCheckForSubProcess extends AbstractXmlCheck {

    private static final Logger LOG = Loggers.get(DeadProcessCheckForSubProcess.class);
	public static final String RULE_KEY = "DeadProcessCheckForSubProcess";

	public static final String STARTER_ELEMENT_NAMESPACE = "http://xmlns.tibco.com/bw/process/2003";
	public static final String STARTER_ELEMENT_NAME = "starter";
	public static final String DEAD_CODE_DESCRIPTION = "Dead Code: This sub process is not used any where in the code";

	@Override
	protected void validate(XmlBw5Source xmlSource) {
		Document document = xmlSource.getDocument(true);
		try {
			Boolean isDynamicExist = checkDynamicCode();
			//System.out.println("Is Dynamic Check for sub process: "+isDynamicExist);
			if (!isDynamicExist) {

				NodeList nodeList = document.getDocumentElement().getElementsByTagNameNS(STARTER_ELEMENT_NAMESPACE,
						STARTER_ELEMENT_NAME);
				File sourceDir = new File("SourceCode\\");
				Node nameNode = document.getDocumentElement().getElementsByTagNameNS(STARTER_ELEMENT_NAMESPACE, "name")
						.item(0);
				String name = nameNode.getTextContent();
				// System.out.println("Name of process" + name);

				if (nodeList.getLength() < 1) {
					Boolean isPresent = false;
					// Subprocess Logic
					String[] processExtensions = new String[] { "process", "serviceagent" };
					List<File> processFiles = (List<File>) FileUtils.listFiles(sourceDir, processExtensions, true);
					// System.out.println("Number of .process files are " +
					// processFiles.size());
					for (File processFile : processFiles) {
						if (name.contains(processFile.getName())) {
							// System.out.println("Process Name
							// "+processFile.getName()+" \n");
							continue;
						} else {
							BufferedReader reader = new BufferedReader(new FileReader(processFile));
							String sCurrLine = "";
							while ((sCurrLine = reader.readLine()) != null) {
								if (sCurrLine.contains(name)) {
									isPresent = true;
								}
							}
							reader.close();
						}
					}

					if (!isPresent) {
                                                reportIssueOnFile("The " + DEAD_CODE_DESCRIPTION);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception Occured in DeadProcessCheckForSubProcess");
			//Violation violation = new DefaultViolation(getRuleKey(), 1, DEAD_CODE_DESCRIPTION);
			//xmlSource.addViolation(violation);
		}
	}

	public static Boolean checkDynamicCode() throws IOException {
		Boolean isDynamicFound = false;
		File sourceDir = new File("SourceCode\\");
		//File sourceDir = new File("C:\\workspace\\BW\\CustomerOrderManagement\\SourceCode");
		String[] processExtensions = new String[] { "process" };
		List<File> processFiles = (List<File>) FileUtils.listFiles(sourceDir, processExtensions, true);
		for (File processFile : processFiles) {
			BufferedReader reader = new BufferedReader(new FileReader(processFile));
			String sCurrLine = "";
			while ((sCurrLine = reader.readLine()) != null) {
				if (sCurrLine.contains("<processNameXPath>") && sCurrLine.contains("</processNameXPath>")) {
					isDynamicFound = true;
					break;
				}
			}
			if (isDynamicFound) {
				break;
			}
			reader.close();

		}
		return isDynamicFound;
	}
	
//	public static void main(String s[]) throws IOException{
//		System.out.println("----> "+checkDynamicCode());
//	}
        
        
    @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }

    @Override
    public Logger getLogger() {
        return LOG;
    }
}
