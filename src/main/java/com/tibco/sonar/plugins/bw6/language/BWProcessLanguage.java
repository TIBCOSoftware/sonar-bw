/*
 * SonarQube Java
 * Copyright (C) 2012 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package com.tibco.sonar.plugins.bw6.language;

import org.apache.commons.lang.StringUtils;
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
		suffixes = StringUtils.split(BW6LanguageFileSuffixProperty.FILE_SUFFIXES_DEFAULT_VALUE, ",");
	  }
	  return suffixes;
	}


	private String[] filterEmptyStrings(String[] stringArray) {
		List<String> nonEmptyStrings = new ArrayList<>();
		for (String string : stringArray) {
		  if (StringUtils.isNotBlank(string.trim())) {
			nonEmptyStrings.add(string.trim());
		  }
		}
		return nonEmptyStrings.toArray(new String[nonEmptyStrings.size()]);
    }
     
}
