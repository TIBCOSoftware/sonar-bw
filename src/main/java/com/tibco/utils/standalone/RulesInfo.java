/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.utils.standalone;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.tibco.sonar.plugins.bw.check.AbstractCheck;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.tibco.sonar.plugins.bw.source.XmlSource;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;

public class RulesInfo {

    final static Pattern p1 = Pattern.compile(".*<h3>.*</h3>.+?<p>(.+)</p>.*<h3>.*</h3>.*<h3>.*</h3>.*",
            Pattern.MULTILINE | Pattern.DOTALL);
    final static Pattern p2 = Pattern.compile(".*<h3>.*</h3>.*<h3>.*</h3>.+?<p>(.+)</p>.*<h3>.*</h3>.*",
            Pattern.MULTILINE | Pattern.DOTALL);
    final static Pattern p3 = Pattern.compile(".*<h3>.*</h3>.*<h3>.*</h3>.*<h3>.*</h3>.+?<p>(.+)</p>.*",
            Pattern.MULTILINE | Pattern.DOTALL);
    final static Pattern[] fields = {p1, p2, p3};

    public final static String PRIORITY_PARAM = "priority";
    public final static String PRIORITY_INFO = "INFO";
    public final static String PRIORITY_MINOR = "MINOR";
    public final static String PRIORITY_MAJOR = "MAJOR";
    public final static String PRIORITY_CRITICAL = "CRITICAL";
    public final static String PRIORITY_BLOCKER = "BLOCKER";
    private final static String[] PRIORITY_ALL = {PRIORITY_INFO, PRIORITY_MINOR, PRIORITY_MAJOR, PRIORITY_CRITICAL,
        PRIORITY_BLOCKER};

    public final static String TYPE_PARAM = "type";
    public final static String TYPE_SMELL = "CODE_SMELL";
    public final static String TYPE_BUG = "BUG";
    public final static String TYPE_SECURITY_HOTSPOT = "SECURITY_HOTSPOT";
    public final static String TYPE_VULNARABILITY = "VULNERABILITY";
    private final static String[] TYPE_ALL = {TYPE_SMELL, TYPE_BUG, TYPE_SECURITY_HOTSPOT, TYPE_VULNARABILITY};

    public class DocumentationException extends Exception {

        private static final long serialVersionUID = 1219818459361699505L;

        public DocumentationException(String string) {
            super(string);
        }
    }

    public RulesInfo() throws DocumentationException {

    }

    public Rule getRuleFromRuleClass(AbstractCheck check) {

        return check.getClass().getAnnotation(org.sonar.check.Rule.class);

    }

    public List<RuleProperty> getRuleParamsFromRuleClass(AbstractCheck check) {
        List<RuleProperty> out = new ArrayList();
        RuleProperty[] annotationArray = check.getClass().getAnnotationsByType(org.sonar.check.RuleProperty.class);
        if(annotationArray != null){
            out.addAll(Arrays.asList(annotationArray));
        }
        return out;

    }

    public String getHTMLDocForRule(String folder,String rule) throws DocumentationException {
        try {
            String htmlPath = "/org/sonar/l10n/"+folder+"/rules/" + rule + ".html";
            String info = new String(RulesInfo.class.getResourceAsStream(htmlPath).readAllBytes());
            return info;
        } catch (IOException e) {
            Logger.getLogger(XmlSource.class.getName()).log(Level.SEVERE, null, e);
            throw new DocumentationException("Cannot read HTML documentation for rule - " + rule);
        } catch (NullPointerException e) {
            throw new DocumentationException("Cannot read HTML documentation for rule - " + rule);
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
