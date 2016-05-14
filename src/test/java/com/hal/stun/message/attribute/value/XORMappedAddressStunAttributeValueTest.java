package com.hal.stun.message.attribute.value;

import com.hal.stun.client.data.ClientTestData;

import org.junit.Test;
import org.junit.Assert;

import java.net.InetSocketAddress;

public class XORMappedAddressStunAttributeValueTest {

  @Test
  public void testInitializeIPv4() throws Exception {
    InetSocketAddress address = new InetSocketAddress("192.0.2.1", 32853);
    byte[] transactionID = ClientTestData.getTransactionID();
    StunAttributeValue attributeValue = new XORMappedAddressStunAttributeValue(address, transactionID);
    byte[] value = attributeValue.getBytes();
    byte[] expectedValue = ClientTestData.getXORMappedAddressBytesV4();
    Assert.assertArrayEquals("XOR mapped addresses should match", expectedValue, value);
  }
}
