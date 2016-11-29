package com.hal.stun.config;

import java.util.Properties;
import java.io.File;
import java.io.FileOutputStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class StunOverridePropertiesTest {

  private File tempFile;

  @Before
  public void beforeEach() throws Exception {
    tempFile = File.createTempFile("stun", "test");
  }

  @Test
  public void testOverrideDefaultProperty() throws Exception {
    Properties properties = new Properties();
    properties.setProperty("server.tcp.serve", "false");
    properties.setProperty("server.threads", "7");
    String overridesFilename = StunOverrideProperties.getResourceFilename();
    tempFile.renameTo(new File(overridesFilename));
    properties.store(new FileOutputStream(overridesFilename), "test");

    StunOverrideProperties overrides = StunOverrideProperties.buildProperties(StunDefaultProperties.buildProperties());

    Assert.assertEquals(false, overrides.getServeTCP());
    Assert.assertEquals(7, overrides.getThreads());
  }

  @After
  public void afterEach() {
    if (tempFile.exists()) {
      tempFile.delete();
    }
  }
}
