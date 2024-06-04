/*
 * Copyright (C) 2016-2023. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.language;

import org.sonar.api.resources.AbstractLanguage;
import java.util.ArrayList;
import java.util.List;
import org.sonar.api.config.Configuration;
import com.tibco.sonar.plugins.bw6.settings.BW6LanguageFileSuffixProperty;

/**
 * Process language implementation
 * 
 * @since 1.3
 */
public class BWProcessLanguage extends AbstractLanguage {

	protected Configuration config;

	public static final String LANGUAGE_NAME = "BusinessWorks 6";
	public static final String KEY = "bw6";

	public BWProcessLanguage(Configuration config) {
    super(KEY, LANGUAGE_NAME);
    this.config = config;
  }

	@Override
	public String[] getFileSuffixes() {
	  String[] suffixes = filterEmptyStrings(config.getStringArray(BW6LanguageFileSuffixProperty.FILE_SUFFIXES_KEY));
	  if (suffixes.length == 0) {
		suffixes = BW6LanguageFileSuffixProperty.FILE_SUFFIXES_DEFAULT_VALUE.split(",");
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
