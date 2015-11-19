package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;
import com.hal.stun.message.StunParseException;

import java.util.Arrays;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

// how to pass the magic cookie?
public class XORMappedAddressStunAttributeValue extends MappedAddressStunAttributeValue {
  public XORMappedAddressStunattributeValue(InetSocketAddress address) {
    super(generateFrom(address));
  }
  
  private static String generateFrom(InetSocketAddress address) {
    InetAddress baseAddress = address.getAddress();
    byte[] addressBytes = baseAddress.get
    byte family;
    short xPort = generateXPort();

    if (address.get
    
    byte[] xAddress = gene
  }
}
