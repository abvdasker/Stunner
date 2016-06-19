package com.hal.stun.cli;

import java.util.Map;
import java.util.HashMap;

import org.junit.Test;
import org.junit.Assert;

public class ArgumentsTest {

  @Test
  public void testGetBoolean() {
    Map<String, String> testMap = new HashMap<String, String>();
    testMap.put("argumentName", "true");

    boolean result = new Arguments(testMap).getBoolean("argumentName");

    Assert.assertTrue(result);
  }

  @Test
  public void testGetInt() throws ArgumentParseException {
    Map<String, String> testMap = new HashMap<String, String>();
    testMap.put("argumentName", "1234");

    int result = new Arguments(testMap).getInt("argumentName");

    Assert.assertEquals(1234, result);
  }

  @Test(expected = ArgumentParseException.class)
  public void testGetInvalidInt() throws ArgumentParseException {
    Map<String, String> testMap = new HashMap<String, String>();
    testMap.put("argumentName", "not_a_number");

    int result = new Arguments(testMap).getInt("argumentName");

    Assert.fail();
  }

  @Test
  public void testGetString() {
    Map<String, String> testMap = new HashMap<String, String>();
    testMap.put("argumentName", "logfile.txt");

    String result = new Arguments(testMap).getString("argumentName");

    Assert.assertEquals("logfile.txt", result);
  }
}
