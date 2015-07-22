package com.elminster.retrieve.util;

import java.io.IOException;

import com.elminster.common.config.CommonConfiguration;

/**
 * The Configuration.
 * 
 * @author jgu
 * @version 1.0
 */
public class Configuration extends CommonConfiguration {
  /** the XBox live URL properties. */
  private static final String XBL_URL_PROPERTIES = "XblUrls.properties";
  /** the user profile xpath properties. */
  private static final String USER_PROFILE_XPATH_PROPERTIES = "UserProfileXPath.properties";
  /** the singleton instance. */
  public static final Configuration INSTANCE = new Configuration();
  
  /**
   * Constructor.
   */
  private Configuration() {
    super();
  }

  /**
   * Load the resource files.
   */
  protected void loadResources() {
    try {
      properties.load(Configuration.class.getClassLoader().getResourceAsStream(XBL_URL_PROPERTIES));
      properties.load(Configuration.class.getClassLoader().getResourceAsStream(USER_PROFILE_XPATH_PROPERTIES));
    } catch (IOException e) {
      throw new IllegalStateException("Cannot initialize the configuration: " + e);
    }
  }
}
