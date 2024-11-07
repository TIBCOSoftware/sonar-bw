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
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;

@Rule(key = DeadProcessCheckForStarterProcess.RULE_KEY, name = CheckConstants.RULE_PROCESS_DEADPROCESSCHECKFORSTARTERPROCESS_NAME, description =  CheckConstants.RULE_PROCESS_DEADPROCESSCHECKFORSTARTERPROCESS_DESCRIPTION, priority = Priority.CRITICAL, tags = { "bug" })
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.CRITICAL)
public class DeadProcessCheckForStarterProcess extends AbstractProcessCheck {

    private static final Logger LOG = LoggerFactory.getLogger(DeadProcessCheckForStarterProcess.class);
	public static final String RULE_KEY = "DeadProcessCheckForStarterProcess";
	public static final String DEAD_CODE_DESCRIPTION = "Dead Code: This Starter process is not used any where in the code";

	@Override
	protected void validate(ProcessSource processSource) {

		try {
			String name =processSource.getProcessModel().getName();
			boolean isPresent = false;
			File sourceDir = processSource.getBaseDir();
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

		} catch (Exception e) {
			LOG.error("Exception Occured in DeadProcessCheckForStarterProcess");
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
