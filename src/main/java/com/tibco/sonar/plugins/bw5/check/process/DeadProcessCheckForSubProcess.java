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

import com.tibco.sonar.plugins.bw5.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw5.source.ProcessSource;
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
public class DeadProcessCheckForSubProcess extends AbstractProcessCheck {

    private static final Logger LOG = Loggers.get(DeadProcessCheckForSubProcess.class);
	public static final String RULE_KEY = "DeadProcessCheckForSubProcess";
	public static final String DEAD_CODE_DESCRIPTION = "Dead Code: This sub process is not used any where in the code";
	public static final String DEAD_CODE_DESCRIPTION_DYNAMIC =
			"Dead Code: This sub process is not used any where in the code."
					+ "But Dynamic Call exists so please check whether this sub process"
					+ " is called dynamically or not. If not this is a Dead Code";
	@Override
	protected void validate(ProcessSource processSource) {

		try {
			File sourceDir = processSource.getBaseDir();
			boolean isDynamicExist = checkDynamicCode(sourceDir);
			String name =processSource.getProcessModel().getName();
			boolean isPresent = false;

			if(processSource.getProcessModel().isSubprocess()) {
				// Subprocess Logic
				isPresent = checkIfProcessReference(sourceDir, name);
				if (!isPresent) {
					if (!isDynamicExist) {
						reportIssueOnFile("The " + DEAD_CODE_DESCRIPTION);
					} else {
						reportIssueOnFile("The " + DEAD_CODE_DESCRIPTION_DYNAMIC);
					}
				}
			}

			

		} catch (Exception e) {
			LOG.error("Exception Occured in DeadProcessCheckForSubProcess");
		}
	}

	private static boolean checkIfProcessReference(File sourceDir, String name) throws IOException {
		boolean isPresent = false;
		String[] processExtensions = new String[]{"process", "serviceagent"};
		List<File> processFiles = (List<File>) FileUtils.listFiles(sourceDir, processExtensions, true);
		for (File processFile : processFiles) {
			if (!name.contains(processFile.getName())) {
				try (BufferedReader reader = new BufferedReader(new FileReader(processFile))) {
					String sCurrLine = "";
					while ((sCurrLine = reader.readLine()) != null) {
						if (sCurrLine.contains(name)) {
							isPresent = true;
						}
					}
				}
			}
		}
		return isPresent;
	}

	public static Boolean checkDynamicCode(File sourceDir) throws IOException {
		boolean isDynamicFound = false;
		String[] processExtensions = new String[] { "process" };
		List<File> processFiles = (List<File>) FileUtils.listFiles(sourceDir, processExtensions, true);
		for (File processFile : processFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(processFile))) {
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
            }

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
