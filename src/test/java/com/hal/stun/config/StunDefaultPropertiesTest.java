package com.hal.stun.config;

import java.util.Properties;

import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;

public class StunDefaultPropertiesTest {

  private static StunDefaultProperties stunProperties;

  @BeforeClass
  public static void beforeAll() {
    stunProperties = StunDefaultProperties.buildProperties();
  }

  @Test
  public void testGetServeTCP() {
    Assert.assertEquals("should serve TCP is true by default",
                        true,
                        stunProperties.getServeTCP());
  }

  @Test
  public void testGetServeUDP() {
    Assert.assertEquals("should serve TCP is true by default",
                        false,
                        stunProperties.getServeUDP());
  }

  @Test
  public void testGetTCPPort() {
    Assert.assertEquals("TCP port default matches",
                        8000,
                        stunProperties.getTCPPort());
  }

  @Test
  public void testGetUDPPort() {
    Assert.assertEquals("UDP port default matches",
                        8001,
                        stunProperties.getUDPPort());
  }

  @Test
  public void testGetThreads() {
    Assert.assertEquals("default 2 threads",
                        2,
                        stunProperties.getThreads());
  }
}
