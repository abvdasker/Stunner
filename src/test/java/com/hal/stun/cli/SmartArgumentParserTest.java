package com.hal.stun.cli;

import org.junit.Test;
import org.junit.Assert;

import java.util.Map;

public class SmartArgumentParserTest {

  @Test
  public void testParseDefaultTCPPort() throws Exception {
    String[] args = new String[0];
    Map<String, Argument> parsedArgs = SmartArgumentParser.parse(args);

    int tcpPort = parsedArgs.get("--tcpport").getInt();
    Assert.assertEquals("returns the default tcp port",
                        SmartArgumentParser.DEFAULT_TCP_PORT,
                        tcpPort);
  }

  @Test
  public void testParseDefaultUDPPort() throws Exception {
    String[] args = new String[0];
    Map<String, Argument> parsedArgs = SmartArgumentParser.parse(args);

    int udpPort = parsedArgs.get("--udpport").getInt();
    Assert.assertEquals("returns the default udp port",
                        SmartArgumentParser.DEFAULT_UDP_PORT,
                        udpPort);
  }

  @Test
  public void testParseDefaultShowHelp() throws Exception {
    String[] args = new String[0];
    Map<String, Argument> parsedArgs = SmartArgumentParser.parse(args);

    boolean showHelp = parsedArgs.get("--help").getBoolean();
    Assert.assertFalse("returns false to not show help by default",
                       showHelp);
  }

  @Test
  public void testParseDefaultUseTCP() throws Exception {
    String[] args = new String[0];
    Map<String, Argument> parsedArgs = SmartArgumentParser.parse(args);

    boolean useTCP = parsedArgs.get("--tcp").getBoolean();
    Assert.assertFalse("returns false to not use TCP by default",
                       useTCP);
  }

  @Test
  public void testParseDefaultUseUDP() throws Exception {
    String[] args = new String[0];
    Map<String, Argument> parsedArgs = SmartArgumentParser.parse(args);

    boolean useUDP = parsedArgs.get("--udp").getBoolean();
    Assert.assertFalse("returns false to not use udp by default",
                       useUDP);
  }

  @Test
  public void testParseDefaultThreadCount() throws Exception {
    String[] args = new String[0];
    Map<String, Argument> parsedArgs = SmartArgumentParser.parse(args);

    int threadCount = parsedArgs.get("--threads").getInt();
    Assert.assertEquals("returns the default thread count",
                        SmartArgumentParser.DEFAULT_THREAD_COUNT,
                        threadCount);
  }

  @Test
  public void testParseShort() throws Exception {
    String[] args = {
      "-tport", "1234",
      "-uport", "5678",
      "-tcp",
      "-udp",
      "-h",
      "-t", "7"
    };
    Map<String, Argument> parsedArgs = SmartArgumentParser.parse(args);
    int tcpPort = parsedArgs.get("--tcpport").getInt();
    int udpPort = parsedArgs.get("--udpport").getInt();
    boolean useTCP = parsedArgs.get("--tcp").getBoolean();
    boolean useUDP = parsedArgs.get("--udp").getBoolean();
    boolean showHelp = parsedArgs.get("--help").getBoolean();
    int threadCount = parsedArgs.get("--threads").getInt();

    Assert.assertEquals(1234, tcpPort);
    Assert.assertEquals(5678, udpPort);
    Assert.assertTrue(useTCP);
    Assert.assertTrue(useUDP);
    Assert.assertTrue(showHelp);
    Assert.assertEquals(7, threadCount);
  }
}
