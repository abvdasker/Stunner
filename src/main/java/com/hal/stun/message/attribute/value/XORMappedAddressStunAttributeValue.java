package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;
import com.hal.stun.message.StunParseException;

import java.util.Arrays;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

// how to pass the magic cookie?
public class XORMappedAddressStunAttributeValue extends MappedAddressStunAttributeValue {

  private static final int MAGIC_COOKIE = 0x;

  // TODO: need to pass in transaction ID for XOR operation
  public XORMappedAddressStunattributeValue(InetSocketAddress address) {
    super(generateFrom(address));
  }
  
  private static String generateFrom(InetSocketAddress address) {
    InetAddress baseAddress = address.getAddress();
    byte[] addressBytes = baseAddress.getAddress();
    byte family = generateFamily();
    short xPort = generateXPort();

    if (addressBytes.length == IPV4_ATTRIBUTE_SIZE) {
      family = generateIPv4Family();
    } else if (addressBytes.length == IPV6_ATTRIBUTE_SIZE) {
      family = generateIPv6Family();
    } else {
      throw new RuntimeException();
    }

    byte[] xAddress = generateXAddress(addressBytes);
  }

  private static byte gemerateFamily() {
  }

  private static short generateXPort() {
    
  }

  private static byte[] generateXAddress(byte[] addressBytes) {
    
  }
}
