package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageTestData;

import org.junit.Test;

import java.net.InetSocketAddress;

public class XORMappedAddressStunAttributeValueTest {
  @Test
  public void testInitializeIPv4() throws Exception {
    InetSocketAddress address = new InetSocketAddress("123.1.0.8", 12);
    byte[] transactionID = StunMessageTestData.getRealTransactionID();
    new XORMappedAddressStunAttributeValue(address, StunMessageTestData.getRealTransactionID());
    // TODO: compare to expected value?
  }
}
