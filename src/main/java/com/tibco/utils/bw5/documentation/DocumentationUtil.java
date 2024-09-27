/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.bw5.documentation;


import com.tibco.sonar.plugins.bw.check.AbstractCheck;
import com.tibco.sonar.plugins.bw5.check.*;
import com.tibco.sonar.plugins.bw5.metric.BusinessWorksMetrics;
import com.tibco.sonar.plugins.bw5.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw5.rulerepository.ProcessRuleDefinition;
import com.tibco.utils.standalone.RulesInfo;
import com.tibco.utils.standalone.RulesInfo.DocumentationException;
import org.sonar.check.RuleProperty;

import java.io.*;

public class DocumentationUtil {

	private static final String CODE_START = "**`";
	private static final String CODE_END = "`**";

	private static final String EMPH_START = "***";
	private static final String EMPH_END = "***";

	private static RulesInfo info;

	private DocumentationUtil() {

	}

	private static int getRulesCount() {
		return ProcessRuleDefinition.getCheckList().size();
	}

	private static int getMeasuresCount() {
		return new BusinessWorksMetrics().getMetrics().size();
	}

	public static void generate() throws FileNotFoundException, DocumentationException {
		System.out.println("***** GENERATING BW5 DOCS *****");

		info = new RulesInfo();

		writeRulesFile();

		ProcessRuleDefinition.getCheckList().forEach(x -> {
			try {
				writeRulesFile(x);
			} catch (DocumentationException ex) {
				System.err.print(ex.getMessage());
				//System.exit(1);
			} catch (IOException ex) {
				ex.printStackTrace(System.err);
				System.exit(1);
			}
		});

		System.err.print(false);
	}

	private static void writeRulesFile() throws FileNotFoundException {
		FileOutputStream fos = new FileOutputStream("docs/rules/bw5/RULES.md");
        try (PrintStream ps = new PrintStream(fos)) {

            ps.println("# Available Quality Rules");
            ps.println();

            ps.println(
                    "This version of the plugin provides " + EMPH_START + getRulesCount() + " quality rules" + EMPH_END
                            + " and [" + EMPH_START + getMeasuresCount() + " metrics" + EMPH_END + "](../METRICS.md). Note that not"
                            + " all of the rules will be available by default. Some rules are disabled - as they may not be applicable to"
                            + " all installations - and some are templates. These must be instantiated as required and configuration parameters provided.");
            ps.println();

            ps.println("| Name | Type | Has Parameters | Initial  State | Description |");
            ps.println("| ---- | ---- | -------------- | -------------- | ----------- |");

            ProcessRuleDefinition.getCheckList().stream().sorted((o1, o2) -> o1.getRuleKeyName().compareTo(o2.getRuleKeyName()))
                    .forEach(rule -> {
                        try {
                            String name = rule.getRuleKeyName();
                            String htmldocs = info.getHTMLDocForRule("bw5", name);
                            String describe = info.getFieldFromHTMLDoc(htmldocs, 1, true);
                            String type = getType(rule);
//TODO Revisar el role
                            String state = rule instanceof XPathCheck ? "Disabled" : "Enabled";
                            ps.println("| [`" + name + "`](" + name + ".md) | " + type + " | " + (info.getRuleParamsFromRuleClass(rule).size() > 0 ? "Yes"
                                    : "No") + " | " + state + " | " + describe + " |");
                        } catch (DocumentationException ex) {
                            System.err.println(ex.getMessage());
//TODO System.exit(1);
                        }
                    });

            ps.println();
            ps.println("---");
            ps.println(
                    "[<< Return to main README file](../../../README.md)");

        }
    }

	private static final String TYPE_PROCESS = "Process";
	private static final String TYPE_PROJECT = "Project";


	private static String getType(AbstractCheck check) {
		if (check instanceof AbstractProcessCheck)
			return TYPE_PROCESS;
		return TYPE_PROJECT;
	}

	private static void writeRulesFile(AbstractCheck check) throws DocumentationException, IOException {

		System.out.println("Generating documentation file " + check.getRuleKeyName() + ".md");

		FileOutputStream fos = new FileOutputStream("docs/rules/bw5/" + check.getRuleKeyName() + ".md");
        try (PrintStream ps = new PrintStream(fos)) {


            String htmldocs = info.getHTMLDocForRule("bw5", check.getRuleKeyName());
            String shortInfo = info.getFieldFromHTMLDoc(htmldocs, 1, false);
            String longInfo = info.getFieldFromHTMLDoc(htmldocs, 2, false);
            String fix = info.getFieldFromHTMLDoc(htmldocs, 3, false);
            String type = getType(check);

            ps.println("# " + check.getRuleKeyName());
            ps.println();

            ps.println("## What condition does this detect?");
            ps.println();
            ps.println(shortInfo);
            ps.println();

            switch (type) {
                case TYPE_PROCESS:
                    ps.println("This is a " + EMPH_START + TYPE_PROCESS + EMPH_END
                            + " rule - the rule will test each process of the application");
                    break;
                case TYPE_PROJECT:
                    ps.println("This is an " + EMPH_START + "Application" + EMPH_END
                            + " rule - the rule will test for some condition within the application");
                    break;
				default: break;
            }
            ps.println();


            ps.println("## Why is this condition important?");
            ps.println();
            ps.println(longInfo);
            ps.println();


            ps.println("## How to fix it?");
            ps.println();
            ps.println(fix);
            ps.println();

            ps.println("## How do I use this rule?");
            ps.println();

            writeSonarqubeSection(ps, check);


            ps.println("---");
            ps.println(
                    "[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)");
        }

	}

	private static void writeSonarqubeSection(PrintStream ps, AbstractCheck check) throws DocumentationException {


                //TODO Do something for XPath Check
		if (check instanceof XPathCheck)
			ps.println("This is a " + EMPH_START + "template" + EMPH_END
			    + " rule. By default it is not enabled or available. To enable it, clone the default \""
			    + CODE_START + BWProcessQualityProfile.PROFILE_NAME + CODE_END
			    + "\" quality profile and then create new rules based on the template. You many create "
			    + "multiple rule instances for a single template, if required, each with different parameters.");
		else
			ps.println("The rule is **_enabled_** by default. To disable it if unwanted, clone the default \""
			    + CODE_START + BWProcessQualityProfile.PROFILE_NAME + CODE_END + "\" quality profile and then disable the rule.");
		ps.println();

		if (!info.getRuleParamsFromRuleClass(check).isEmpty()) {
			ps.println(
			    "This rule has configuration parameters that may be set within the SonarQube system. To change values clone the default \""
			        + CODE_START
			        + BWProcessQualityProfile.PROFILE_NAME
			        + CODE_END + "\" quality profile and then set any required values.");
			ps.println();

			ps.println("| Parameter | Description | Default |");
			ps.println("| --------- | ----------- | ------- |");

			for (RuleProperty param : info.getRuleParamsFromRuleClass(check)) {
				ps.println("| " + param.key() + " | " + param.description()+ " | " + param.defaultValue()+ " |");
			}

			ps.println();
		}

	}

	

}