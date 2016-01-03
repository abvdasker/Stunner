package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;
import com.hal.stun.message.StunParseException;
import com.hal.stun.message.StunHeader;
import com.hal.stun.message.MagicCookie;

import java.util.Arrays;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

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
    byte[] value;
    if (addressBytes.length == IPV4_ADDRESS_SIZE) {
      family = IPV4_FAMILY_CODE;
      xAddress = generateIPV4XAddress(addressBytes);
      value = new byte[IPV4_ATTRIBUTE_SIZE];
    } else if (addressBytes.length == IPV6_ADDRESS_SIZE) {
      family = IPV6_FAMILY_CODE;
      xAddress = generateIPV6XAddress(addressBytes, transactionID);
      value = new byte[IPV6_ATTRIBUTE_SIZE];
    } else {
      throw new RuntimeException(
                                 "address is not the right size. It was " + addressBytes.length +
                                 " bytes. XOR Mapped Address attributes must be either " +
                                 IPV4_ADDRESS_SIZE + " or " + IPV6_ADDRESS_SIZE + " bytes.");
    }

    combineXORMappedAddressComponents(value, family, xPort, xAddress);

    return value;
  }

  private static void combineXORMappedAddressComponents(byte[] combined, byte family, short xPort, byte[] xAddress) {
    combined[1] = family;
    combined[2] = unsignByte(xPort >>> 8);
    combined[3] = (byte) xPort;

    System.arraycopy(xAddress,
                     0,
                     combined,
                     4,
                     xAddress.length);
  }

  private static short generateXPort(int port) {
    byte lowerByte = (byte) (port);
    byte upperByte = (byte) (port >>> 8);

    short magicCookieTopBytes = MagicCookie.getTopTwoBytes();
    byte magicCookieTopByte = unsignByte(magicCookieTopBytes >>> 8);
    byte magicCookieSecondTopByte = (byte) (magicCookieTopBytes);

    byte xoredUpperByte = unsignByte(upperByte ^ magicCookieTopByte);
    byte xoredLowerByte = unsignByte(lowerByte ^ magicCookieSecondTopByte);

    short xPort = unsignShort(xoredUpperByte);
    xPort <<= 8;
    xPort |= unsignShort(xoredLowerByte);

    return xPort;
  }

  private static byte[] generateIPV4XAddress(byte[] address) {
    byte[] magicCookieBytes = MagicCookie.getBytesBigEndian();
    byte[] xAddress = new byte[address.length];
    for (int i = 0; i < xAddress.length; i++) {
      xAddress[i] = unsignByte(magicCookieBytes[i] ^ address[i]);
    }
    return xAddress;
  }

  private static byte[] generateIPV6XAddress(byte[] address, byte[] transactionID) {
    byte[] magicCookieBytes = MagicCookie.getBytesBigEndian();
    byte[] xAddress = new byte[address.length];

    byte[] magicCookiePlusTransactionID = combineArrays(magicCookieBytes, transactionID);

    for (int i = 0; i < address.length; i++) {
      byte operand = magicCookiePlusTransactionID[i];
      xAddress[i] = unsignByte(operand ^ address[i]);
    }

    return xAddress;
  }

  private static byte[] combineArrays(byte[] first, byte[] second) {
    byte[] destination = new byte[first.length + second.length];
    System.arraycopy(
                     first,
                     0,
                     destination,
                     0,
                     first.length);

    System.arraycopy(
                     second,
                     0,
                     destination,
                     first.length,
                     second.length);

    return destination;
  }

  private static byte unsignByte(int number) {
    return (byte) (number & StunMessageUtils.MASK);
  }

  private static short unsignShort(int number) {
    return (short) (number & StunMessageUtils.MASK);
  }
}
