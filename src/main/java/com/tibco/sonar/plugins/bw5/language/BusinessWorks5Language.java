/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw5.language;

import java.util.ArrayList;

import com.tibco.sonar.plugins.bw5.settings.BW5LanguageFileSuffixProperty;
import org.sonar.api.resources.AbstractLanguage;

import java.util.List;
import org.sonar.api.config.Configuration;

/**
 * This class defines the XML language.
 *
 * @author Matthijs Galesloot
 */
public class BusinessWorks5Language extends AbstractLanguage {

    protected Configuration config;

    public static final String LANGUAGE_NAME = "BusinessWorks 5";
    public static final String KEY = "bw5";

    public BusinessWorks5Language(Configuration config) {
        super(KEY, LANGUAGE_NAME);
        this.config = config;
    }

    @Override
    public String[] getFileSuffixes() {
        String[] suffixes = filterEmptyStrings(config.getStringArray(BW5LanguageFileSuffixProperty.FILE_SUFFIXES_KEY));
        if (suffixes.length == 0) {
            suffixes = BW5LanguageFileSuffixProperty.FILE_SUFFIXES_DEFAULT_VALUE.split(",");
        }
        return suffixes;
    }

    private String[] filterEmptyStrings(String[] stringArray) {
        List<String> nonEmptyStrings = new ArrayList<>();
        for (String string : stringArray) {
            if (!string.trim().isBlank()) {
                nonEmptyStrings.add(string.trim());
            }
        }
        return nonEmptyStrings.toArray(new String[nonEmptyStrings.size()]);
    }

}
