package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;

import org.junit.Test;
import org.junit.Assert;

import java.net.InetAddress;

public class MappedAddressStunAttributeValueTest {

    private static final String ATTRIBUTE_VALUE_HEX = "000107d07f000001";

    @Test
    public void testInitializeIPv4() throws Exception {
        // port 2000 = 07d0
        // ipv4 127.0.0.1 = 7f.00.00.01

        byte[] attributeValueBytes = StunMessageUtils.convertHexToByteArray(ATTRIBUTE_VALUE_HEX);
        MappedAddressStunAttributeValue attributeValue = new MappedAddressStunAttributeValue(attributeValueBytes);
        Assert.assertEquals("address family should be ip v4", attributeValue.getAddressFamily(), (byte) 0x01);
        Assert.assertEquals("port should match", 2000, attributeValue.getPort());

        InetAddress address = InetAddress.getByName("127.0.0.1");
        Assert.assertEquals("address should match", address, attributeValue.getAddress());
    }
}
