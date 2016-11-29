package com.hal.stun.config;

import java.util.Properties;

import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;

public class StunPropertiesTest {

  private static StunProperties stunProperties;

  @BeforeClass
  public static void beforeAll() {
    stunProperties = new StunProperties();
  }

  @Test
  public void testGetServeTCP() {
    Assert.assertEquals("should serve TCP is true by default",
                        true,
                        stunProperties.getServeTCP());
  }
}
