/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author avazquez
 */
public class WildcardPattern {

  private static final Map<String, WildcardPattern> CACHE = Collections.synchronizedMap(new HashMap<>());
  private static final String SPECIAL_CHARS = "()[]^$.{}+|";

  private Pattern pattern;
  private String stringRepresentation;

  protected WildcardPattern(String pattern, String directorySeparator) {
    this.stringRepresentation = pattern;
    this.pattern = Pattern.compile(toRegexp(pattern, directorySeparator));
  }

  private static String toRegexp(String antPattern, String directorySeparator) {
    final String escapedDirectorySeparator = '\\' + directorySeparator;

    final StringBuilder sb = new StringBuilder(antPattern.length());

    sb.append('^');

    int i = antPattern.startsWith("/") || antPattern.startsWith("\\") ? 1 : 0;
    while (i < antPattern.length()) {
      final char ch = antPattern.charAt(i);

      if (SPECIAL_CHARS.indexOf(ch) != -1) {
        // Escape regexp-specific characters
        sb.append('\\').append(ch);
      } else if (ch == '*') {
        if (i + 1 < antPattern.length() && antPattern.charAt(i + 1) == '*') {
          // Double asterisk
          // Zero or more directories
          if (i + 2 < antPattern.length() && isSlash(antPattern.charAt(i + 2))) {
            sb.append("(?:.*").append(escapedDirectorySeparator).append("|)");
            i += 2;
          } else {
            sb.append(".*");
            i += 1;
          }
        } else {
          // Single asterisk
          // Zero or more characters excluding directory separator
          sb.append("[^").append(escapedDirectorySeparator).append("]*?");
        }
      } else if (ch == '?') {
        // Any single character excluding directory separator
        sb.append("[^").append(escapedDirectorySeparator).append("]");
      } else if (isSlash(ch)) {
        // Directory separator
        sb.append(escapedDirectorySeparator);
      } else {
        // Single character
        sb.append(ch);
      }

      i++;
    }

    sb.append('$');

    return sb.toString();
  }

  private static boolean isSlash(char ch) {
    return ch == '/' || ch == '\\';
  }

  /**
   * Returns string representation of this pattern.
   * 
   * @since 2.5
   */
  @Override
  public String toString() {
    return stringRepresentation;
  }

  /**
   * Returns true if specified value matches this pattern.
   */
  public boolean match(String value) {
    value = StringUtils.removeStart(value, "/");
    value = StringUtils.removeEnd(value, "/");
    return pattern.matcher(value).matches();
  }

  /**
   * Returns true if specified value matches one of specified patterns.
   * 
   * @since 2.4
   */
  public static boolean match(WildcardPattern[] patterns, String value) {
    for (WildcardPattern pattern : patterns) {
      if (pattern.match(value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Creates pattern with "/" as a directory separator.
   * 
   * @see #create(String, String)
   */
  public static WildcardPattern create(String pattern) {
    return create(pattern, "/");
  }

  /**
   * Creates array of patterns with "/" as a directory separator.
   * 
   * @see #create(String, String)
   */
  public static WildcardPattern[] create(String[] patterns) {
    if (patterns == null) {
      return new WildcardPattern[0];
    }
    WildcardPattern[] exclusionPAtterns = new WildcardPattern[patterns.length];
    for (int i = 0; i < patterns.length; i++) {
      exclusionPAtterns[i] = create(patterns[i]);
    }
    return exclusionPAtterns;
  }

  /**
   * Creates pattern with specified separator for directories.
   * <p>
   * This is used to match Java-classes, i.e. <code>org.foo.Bar</code> against <code>org/**</code>.
   * <b>However usage of character other than "/" as a directory separator is misleading and should be avoided,
   * so method {@link #create(String)} is preferred over this one.</b>
   * 
   * <p>
   * Also note that no matter whether forward or backward slashes were used in the <code>antPattern</code>
   * the returned pattern will use <code>directorySeparator</code>.
   * Thus to match Windows-style path "dir\file.ext" against pattern "dir/file.ext" normalization should be performed.
   * 
   */
  public static WildcardPattern create(String pattern, String directorySeparator) {
    String key = pattern + directorySeparator;
    return CACHE.computeIfAbsent(key, k -> new WildcardPattern(pattern, directorySeparator));
  }
  
  public static void main(String[] args){
     boolean test =  WildcardPattern.create("*.bwp","/").match("test/Process.bwp");
      System.out.println(test);
  }
}
