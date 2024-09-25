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
import java.util.LinkedList;
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

@Rule(key = DeadProcessCheckForDynamicSubProcess.RULE_KEY, name = CheckConstants.RULE_PROCESS_DEADPROCESSCHECKFORDYNAMICSUBPROCESS_NAME, description =  CheckConstants.RULE_PROCESS_DEADPROCESSCHECKFORDYNAMICSUBPROCESS_DESCRIPTION, priority = Priority.INFO)
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.CRITICAL)
public class DeadProcessCheckForDynamicSubProcess extends AbstractXmlCheck {

    private static final Logger LOG = Loggers.get(DeadProcessCheckForDynamicSubProcess.class);
	public static final String RULE_KEY = "DeadProcessCheckForDynamicSubProcess";

	public static final String STARTER_ELEMENT_NAMESPACE = "http://xmlns.tibco.com/bw/process/2003";
	public static final String STARTER_ELEMENT_NAME = "starter";
	public static final String DEAD_CODE_DESCRIPTION =
			"Dead Code: This sub process is not used any where in the code."
			+ "But Dynamic Call exists so please check whether this sub process"
			+ " is called dynamically or not. If not this is a Dead Code";
	public static List<String> dynamicProcesses=null;
	
	@Override
	protected void validate(XmlBw5Source xmlSource) {
		Document document = xmlSource.getDocument(true);
		try {
			Boolean isDynamicExist = checkDynamicCode();
			//System.out.println("Is Dynamic Check: "+isDynamicExist);
			if (isDynamicExist) {
				
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
							// System.out.println("Process Name:
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
						String description="Dead Code: This sub process is not used any where in the code."
								+ "But Dynamic Call (";
						for (String dynamicCall : dynamicProcesses) {
							description=description+dynamicCall+" ";
						}
						description=description+" )";
						description=description+ " exists so please check whether this sub process"
								+ " is called dynamically or not. If not this is a Dead Code";						
                                                reportIssueOnFile("The " + description);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception Occured in DeadProcessCheckForDynamicSubProcess");
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
		dynamicProcesses=new LinkedList<String>();
		for (File processFile : processFiles) {
			BufferedReader reader = new BufferedReader(new FileReader(processFile));
			String sCurrLine = "";
			while ((sCurrLine = reader.readLine()) != null) {
				if (sCurrLine.contains("<processNameXPath>") && sCurrLine.contains("</processNameXPath>")) {
					isDynamicFound = true;
					dynamicProcesses.add(processFile.getName());
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
        
        
    @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }

    @Override
    public Logger getLogger() {
        return LOG;
    }
}
