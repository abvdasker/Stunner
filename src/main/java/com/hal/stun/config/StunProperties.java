package com.hal.stun.config;

import java.util.Properties;
import java.io.InputStream;

import java.io.IOException;

public class StunProperties extends Properties {

  private static final String DEFAULT_PROPERTIES = "/resources/default.properties";
  private static final String OVERRIDE_PATH = "stunner.properties";

  public StunProperties() {
    super(loadDefault());
  }

  private static Properties loadDefault() {
    Properties defaults = new Properties();
    try {
      InputStream propertiesInputStream = StunProperties.class.getResourceAsStream(DEFAULT_PROPERTIES);
      defaults.load(propertiesInputStream);
      propertiesInputStream.close();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
    return defaults;
  }

  public boolean getServeTCP() {
    String serveTCP = getProperty("server.tcp.serve");
    return Boolean.parseBoolean(serveTCP);
  }

  public boolean getServeUDP() {
    String serveUDP = getProperty("server.tcp.serve");
    return Boolean.parseBoolean(serveUDP);
  }
}
