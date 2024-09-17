/*
 * SonarQube XML Plugin
 * Copyright (C) 2010 SonarSource
 * dev@sonar.codehaus.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
