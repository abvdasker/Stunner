package com.hal.stun.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class StunOverrideProperties extends StunProperties {

  private static final String OVERRIDE_FILE = "stunner.properties";

  private StunOverrideProperties(StunProperties defaults) {
    super(defaults);
  }

  public static StunOverrideProperties buildProperties(StunProperties defaults) {
    StunOverrideProperties properties = new StunOverrideProperties(defaults);
    try {
      if (canLoad()) {
        properties.load(new FileInputStream(getResourceFilename()));
      }
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
    return properties;
  }

  static boolean canLoad() {
    return new File(getResourceFilename()).exists();
  }

  static String getResourceFilename() {
    try {
      String currentPath = StunOverrideProperties.
        class.
        getProtectionDomain().
        getCodeSource().
        getLocation().
        toURI().
        getPath();
      return currentPath + "/" + OVERRIDE_FILE;
    } catch (URISyntaxException exception) {
      throw new RuntimeException(exception);
    }
  }
}
