package com.hal.stun.message;

import java.net.InetSocketAddress;

import org.junit.Test;

public class StunSuccessResponseMessageTest {
    @Test
    public void testInitialize() throws StunParseException {
        InetSocketAddress requestAddress = new InetSocketAddress(StunMessageTestData.REAL_STUN_ADDRESS, 1234);
        StunRequestMessage request = new StunRequestMessage(StunMessageTestData.getRealStunMessageBytes(),
                requestAddress);

        new StunSuccessResponseMessage(request);
    }
}
