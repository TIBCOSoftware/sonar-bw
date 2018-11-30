
package com.tibco.sonar.plugins.bw6.settings;

//import java.util.List;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;
import com.tibco.sonar.plugins.bw6.language.BWProcessLanguage;

//import static java.util.Arrays.asList;

public class BW6LanguageFileSuffixProperty {

  public static final String CATEGORY = "TIBCO";
  public static final String SUB_CATEGORY = "BusinessWorks";

  public static final String FILE_SUFFIXES_KEY = "sonar.bw6.file.suffixes";
	public static final String[] DEFAULT_FILE_SUFFIXES = { ".bwp" };
	public static final String FILE_SUFFIXES_DEFAULT_VALUE = ".bwp";

  private BW6LanguageFileSuffixProperty() {
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
