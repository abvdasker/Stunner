package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;
import com.hal.stun.message.StunParseException;

import java.util.Arrays;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

// how to pass the magic cookie?
public class XORMappedAddressStunAttributeValue extends MappedAddressStunAttributeValue {

  // TODO: need to pass in transaction ID for XOR operation
  public XORMappedAddressStunAttributeValue(InetSocketAddress address, byte[] transactionID) throws StunParseException {
    super(generateFrom(address, transactionID));
  }
  
  private static String generateFrom(InetSocketAddress address, byte[] transactionID) {
    InetAddress baseAddress = address.getAddress();
    byte[] addressBytes = baseAddress.getAddress();
    byte family = generateFamily();
    short xPort = generateXPort();

    if (addressBytes.length == IPV4_ATTRIBUTE_SIZE) {
      family = generateIPv4Family();
    } else if (addressBytes.length == IPV6_ATTRIBUTE_SIZE) {
      family = generateIPv6Family(transactionID);
    } else {
      throw new RuntimeException();
    }

    byte[] xAddress = generateXAddress(addressBytes, transactionID);

    return "";
  }

  private static byte[] combineXORMappedAddressComponents(byte family, short xPort, byte[] xAddress) {
    return null;
  }

  private static byte generateFamily() {
    return (byte) 0;
  }

  private static byte generateIPv4Family() {
    return (byte) 0;
  }

  private static byte generateIPv6Family(byte[] transactionID) {
    return (byte) 0;
  }

  private static short generateXPort() {
    return (short) 0;
  }

  private static byte[] generateXAddress(byte[] addressBytes, byte[] transactionID) {
    return new byte[0];
  }
}
