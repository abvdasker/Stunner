package com.hal.stun.config;

import java.util.Properties;
import java.io.InputStream;

import java.io.IOException;

public abstract class StunProperties extends Properties {

  protected StunProperties() {
    super();
  }

  protected StunProperties(Properties properties) {
    super(properties);
  }

  public static StunProperties buildProperties() {
    StunDefaultProperties defaultProperties = StunDefaultProperties.buildProperties();
    if (StunOverrideProperties.canLoad()) {
      return StunOverrideProperties.buildProperties(defaultProperties);
    } else {
      return defaultProperties;
    }
  }

  public boolean getServeTCP() {
    String serveTCP = getProperty("server.tcp.serve");
    return Boolean.parseBoolean(serveTCP);
  }

  public boolean getServeUDP() {
    String serveUDP = getProperty("server.udp.serve");
    return Boolean.parseBoolean(serveUDP);
  }

  public int getTCPPort() {
    String tcpPort = getProperty("server.tcp.port");
    return Integer.parseInt(tcpPort);
  }

  public int getUDPPort() {
    String udpPort = getProperty("server.udp.port");
    return Integer.parseInt(udpPort);
  }

  public int getThreads() {
    String threads = getProperty("server.threads");
    return Integer.parseInt(threads);
  }
}
