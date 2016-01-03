package com.hal.stun.message;

import java.net.InetSocketAddress;

import org.junit.Test;

public class StunResponseMessageTest {
  @Test
  public void testInitialize() throws StunParseException {
    InetSocketAddress requestAddress = new InetSocketAddress(StunMessageTestData.REAL_STUN_ADDRESS, 1234);
    StunMessage request = new StunMessage(
                                          StunMessageTestData.getRealStunMessageBytes(),
                                          requestAddress);

    StunResponseMessage response = new StunResponseMessage(request);
  }
}
