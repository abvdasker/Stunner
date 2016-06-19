package com.hal.stun.cli;

import org.junit.Test;
import org.junit.Assert;

public class ArgumentParserTest {

  @Test
  public void testParseFlag() throws ArgumentParseException {
    String[] arguments = { "--udp" };
    Arguments parsedArgs = new ArgumentParser(arguments).parse();

    boolean runUdp = parsedArgs.getBoolean("--udp");

    Assert.assertTrue("flag argument should return true", runUdp);
  }

  @Test
  public void testParsePair() throws ArgumentParseException {
    String[] arguments = { "--tcpport", "1234" };
    Arguments parsedArgs = new ArgumentParser(arguments).parse();

    int tcpPort = parsedArgs.getInt("--tcpport");

    Assert.assertEquals("int argument should match string input", 1234, tcpPort);
  }

  @Test
  public void testParseDefaultTCPPort() throws ArgumentParseException {
    String[] arguments = {};
    Arguments parsedArgs = new ArgumentParser(arguments).parse();

    int tcpPort = parsedArgs.getInt("--tcpport");

    Assert.assertEquals("default port should be returned when one is not given",
                        ArgumentParser.DEFAULT_TCP_PORT, tcpPort);
  }

  @Test
  public void testDefaultTCP() throws ArgumentParseException {
    String[] arguments = {};
    Arguments parsedArgs = new ArgumentParser(arguments).parse();

    boolean runTCP = parsedArgs.getBoolean("--tcp");

    Assert.assertEquals("tcp should be true by default", true, runTCP);
  }

  @Test
  public void testUDPNotTCP() throws ArgumentParseException {
    String[] arguments = { "--udp" };
    Arguments parsedArgs = new ArgumentParser(arguments).parse();

    boolean runTCP = parsedArgs.getBoolean("--tcp");
    boolean runUDP = parsedArgs.getBoolean("--udp");

    Assert.assertEquals("tcp should be false when it is not given and udp is given as an arg",
                        false, runTCP);
    Assert.assertEquals("udp should be true when given as an arg", true, runUDP);
  }

  @Test(expected = ArgumentParseException.class)
  public void testArgumentWithNoValue() throws ArgumentParseException {
    String[] arguments = { "--threads" };

    new ArgumentParser(arguments).parse();

    Assert.fail();
  }
}
