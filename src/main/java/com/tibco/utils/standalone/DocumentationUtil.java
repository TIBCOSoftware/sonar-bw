/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.standalone;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;


import com.tibco.sonar.plugins.bw.check.AbstractCheck;
import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.check.AbstractProjectCheck;
import com.tibco.sonar.plugins.bw6.check.AbstractResourceCheck;
import com.tibco.sonar.plugins.bw6.check.XPathCheck;

import com.tibco.sonar.plugins.bw6.measurecomputer.resources.AbstractResourceTotals;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.rulerepository.ProcessRuleDefinition;
import com.tibco.utils.standalone.RulesInfo.DocumentationException;
import org.sonar.check.RuleProperty;

public class DocumentationUtil {

	private final static String CODE_START = "**`";
	private final static String CODE_END = "`**";

	private final static String EMPH_START = "***";
	private final static String EMPH_END = "***";

	private static RulesInfo info;

	private final static int getRulesCount() {
		return ProcessRuleDefinition.getCheckList().size();
	}

	private final static int getMeasuresCount() {
		String measuresSrcDir = "./src/main/java/" + AbstractResourceTotals.class.getPackageName().replaceAll("\\.", "/");
		int x = new File(measuresSrcDir).listFiles(file -> file.isFile() && file.getName().endsWith("Measure.java")).length;
		return x;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("***** GENERATING DOCUMENTATION *****");
		com.tibco.utils.bw5.documentation.DocumentationUtil.generate();
		com.tibco.utils.bw6.documentation.DocumentationUtil.generate();
		System.out.println("***** DOCUMENTATION GENERATED *****");

	}




	

}