package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;
import com.hal.stun.message.StunMessageTestData;

import org.junit.Test;
import org.junit.Assert;

import java.net.InetSocketAddress;

public class XORMappedAddressStunAttributeValueTest {
  @Test
  public void testInitializeIPv4() throws Exception {
    InetSocketAddress address = new InetSocketAddress("123.1.0.8", 12);
    byte[] transactionID = StunMessageTestData.getRealTransactionID();
    XORMappedAddressStunAttributeValue value = new XORMappedAddressStunAttributeValue(address, StunMessageTestData.getRealTransactionID());
  }
}
