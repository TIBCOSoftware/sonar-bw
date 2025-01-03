/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.utils.standalone;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import com.tibco.sonar.plugins.bw.check.AbstractCheck;
import org.sonar.check.RuleProperty;

public class RulesInfo {

    static final Pattern p1 = Pattern.compile(".*<h3>.*</h3>.+?<p>(.+)</p>.*<h3>.*</h3>.*<h3>.*</h3>.*",
            Pattern.MULTILINE | Pattern.DOTALL);
    static final Pattern p2 = Pattern.compile(".*<h3>.*</h3>.*<h3>.*</h3>.+?<p>(.+)</p>.*<h3>.*</h3>.*",
            Pattern.MULTILINE | Pattern.DOTALL);
    static final Pattern p3 = Pattern.compile(".*<h3>.*</h3>.*<h3>.*</h3>.*<h3>.*</h3>.+?<p>(.+)</p>.*",
            Pattern.MULTILINE | Pattern.DOTALL);
    static final Pattern[] fields = {p1, p2, p3};


    public static class DocumentationException extends Exception {

        private static final long serialVersionUID = 1219818459361699505L;

        public DocumentationException(String string) {
            super(string);
        }
    }


    public List<RuleProperty> getRuleParamsFromRuleClass(AbstractCheck check) {
        List<RuleProperty> out = new ArrayList<>();
        RuleProperty[] annotationArray = check.getClass().getAnnotationsByType(org.sonar.check.RuleProperty.class);
        if(annotationArray != null){
            out.addAll(Arrays.asList(annotationArray));
        }
        return out;

    }

    public String getHTMLDocForRule(String folder,String rule) throws DocumentationException {
        try {
            String htmlPath = System.getProperty("user.dir") + "/src/main/resources/org/sonar/l10n/"+folder+"/rules/" + rule + ".html";
            String content = "";
            try (FileInputStream inputStream = new FileInputStream(htmlPath)) {
                content = new String(inputStream.readAllBytes());
            }
            return content;

        } catch (IOException | NullPointerException e) {
            throw new DocumentationException("Cannot read HTML documentation for rule - " + rule + ": " + e.getMessage());
        }
    }

    private final String replaceAll(String source, String regex, String replace) {
        String oldValue = "";
        String response = source;
        do {
            oldValue = response;
            response = response.replaceAll(regex, replace);
        } while (!oldValue.equals(response));

        return response;
    }

    public String getFieldFromHTMLDoc(String info, int field, boolean firstOnly) {

        String response = fields[field - 1].matcher(info).replaceFirst("$1");

        if (response.equals(info) || response.equals("")) {
            return "";
        }

        // Protect spaces and newlines within "<pre>"
        response = replaceAll(response, "(<pre>[^<]*) ([^<]*</pre>)", "$1\t$2");
        response = replaceAll(response, "(<pre>[^<]*)\n([^<]*</pre>)", "$1\b$2");

        // Remove spaces and newlines
        response = replaceAll(response, "\n", " ");
        response = replaceAll(response, "\r", "");
        response = replaceAll(response, "  +", " ");

        // Convert "<code>" to markdown
        response = replaceAll(response, "<code>", "**`");
        response = replaceAll(response, "</code>", "`**");

        // Replace spaces and newlines within "<pre>"
        response = replaceAll(response, "\t", " ");
        response = replaceAll(response, "\b", "\n");

        // Convert "<pre>" to markdown
        response = replaceAll(response, " *<pre>", "\n\n```");
        response = replaceAll(response, " *</pre>", "```\n\n");

        if (firstOnly) {
            return response.replaceFirst("(.*?)</p>.*", "$1").trim();
        } else {
            return response.replaceAll(" *</p>( *?)<p> *", "\n\n").trim();
        }
    }

}
