package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;
import com.hal.stun.message.StunParseException;
import com.hal.stun.message.StunHeader;
import com.hal.stun.message.MagicCookie;

import java.util.Arrays;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

// how to pass the magic cookie?
public class XORMappedAddressStunAttributeValue extends MappedAddressStunAttributeValue {

  public XORMappedAddressStunAttributeValue(byte[] value) throws StunParseException {
    super(value);
  }

  // TODO: need to pass in transaction ID for XOR operation
  public XORMappedAddressStunAttributeValue(InetSocketAddress address, byte[] transactionID) throws StunParseException {
    super(generateFrom(address, transactionID));
  }

  private static byte[] generateFrom(InetSocketAddress address, byte[] transactionID) {
    InetAddress baseAddress = address.getAddress();
    byte[] addressBytes = baseAddress.getAddress();

    short xPort = generateXPort(address.getPort());
    byte family;
    byte[] xAddress;
    if (addressBytes.length == IPV4_ATTRIBUTE_SIZE) {
      family = IPV4_FAMILY_CODE;
      xAddress = generateIPV4XAddress(addressBytes);
    } else if (addressBytes.length == IPV6_ATTRIBUTE_SIZE) {
      family = IPV6_FAMILY_CODE;
      xAddress = generateIPV6XAddress(addressBytes, transactionID);
    } else {
      throw new RuntimeException();
    }

    return new byte[0];
  }

  private static byte[] combineXORMappedAddressComponents(byte family, short xPort, byte[] xAddress) {
    return null;
  }

  private static short generateXPort(int port) {
    byte lowerByte = (byte) (port);
    byte upperByte = (byte) (port >>> 8);

    short magicCookieTopBytes = MagicCookie.getTopTwoBytes();
    byte magicCookieTopByte = (byte) ((magicCookieTopBytes >>> 8) & StunMessageUtils.MASK);
    byte magicCookieSecondTopByte = (byte) (magicCookieTopBytes);

    byte xoredUpperByte = (byte) ((upperByte ^ magicCookieTopByte) & StunMessageUtils.MASK);
    byte xoredLowerByte = (byte) ((lowerByte ^ magicCookieSecondTopByte) & StunMessageUtils.MASK);

    short xPort = (short) (xoredUpperByte & StunMessageUtils.MASK);
    xPort <<= 8;
    xPort |= (short) (xoredLowerByte & StunMessageUtils.MASK);

    return xPort;
  }

  private static byte[] generateIPV4XAddress(byte[] address) {
    return new byte[0];
  }

  private static byte[] generateIPV6XAddress(byte[] address, byte[] transactionID) {
    return new byte[0];
  }
}
