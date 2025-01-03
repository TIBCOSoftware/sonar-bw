/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.settings;


import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

public class BW5LanguageFileSuffixProperty {

    public static final String CATEGORY = "TIBCO";
    public static final String SUB_CATEGORY = "BusinessWorks 5.x";

    public static final String BW5_PROCESS_FILE_SUFFIX = ".process";


    public static final String FILE_SUFFIXES_KEY = "sonar.bw5.file.suffixes";
    protected static final String[] DEFAULT_FILE_SUFFIXES = {BW5_PROCESS_FILE_SUFFIX,".substvar", ".adb",  ".adr3", ".serviceagent",  ".sharedhttp", ".sharedjdbc", ".sharedjmscon"};
    public static final String FILE_SUFFIXES_DEFAULT_VALUE = BW5_PROCESS_FILE_SUFFIX;

    private BW5LanguageFileSuffixProperty() {
        // only static definition.
    }

    public static PropertyDefinition getPropertyDefinition() {
        return PropertyDefinition.builder(FILE_SUFFIXES_KEY)
                .defaultValue(FILE_SUFFIXES_DEFAULT_VALUE)
                .category(CATEGORY)
                .name("File Suffixes")
                .description("File suffixes for files to analyze.")
                .onQualifiers(Qualifiers.PROJECT)
                .subCategory(SUB_CATEGORY)
                .multiValues(true)
                .build();
    }

}
