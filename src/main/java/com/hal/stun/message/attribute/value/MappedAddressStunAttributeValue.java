package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;
import com.hal.stun.message.StunParseException;

import java.util.Arrays;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MappedAddressStunAttributeValue extends StunAttributeValue {
  
  // 8 bytes OR 20 bytes
  // 0x0001 - type;
  // 0x0000 - length;
  // value
  //   0x00 empty byte
  //   0x00 address family;
  //   0x0000 port;
  //   0x0000 0000 ipv4 address
  //     OR
  //   0x0000 0000 0000 0000 0000 0000 0000 0000 ipv6 address
  //
  private static final byte IPV4_FAMILY_CODE = (byte) 0x01;
  private static final byte IPV6_FAMILY_CODE = (byte) 0x02;

  private static final int IPV4_ATTRIBUTE_SIZE = 8;
  private static final int IPV6_ATTRIBUTE_SIZE = 20;
  
  private byte addressFamily;
  private short port;
  private InetAddress address;
  
  public MappedAddressStunAttributeValue(String valueHex) throws StunParseException {
    super(valueHex);
  }
  
  public byte getAddressFamily() {
    return addressFamily;
  }
  
  public short getPort() {
    return port;
  }
  
  public InetAddress getAddress() {
    return address;
  }
  
  protected void parseValueBytes() throws StunParseException {
    addressFamily = valueBytes[1];
    port = parsePort();
    
    byte[] addressBytes = Arrays.copyOfRange(valueBytes, 4, valueBytes.length);
    try {
      address = InetAddress.getByAddress(addressBytes);
    } catch (UnknownHostException exception) {
      throw new StunParseException("could not parse internet address", exception);
    }
  }
  
  private short parsePort() {
    short thisPort = 0;
    thisPort = valueBytes[2];
    thisPort <<= 8;
    thisPort |= (valueBytes[3] & StunMessageUtils.MASK);
    return thisPort;
  }
  
  private String parseAddressHex() throws StunParseException {
    if (addressFamily == IPV4_FAMILY_CODE) {
      // extract 4 bytes for IPv4 address
      return StunMessageUtils.extractByteSequenceAsHex(valueBytes, 4, 4, true);
    } else if (addressFamily == IPV6_FAMILY_CODE) {
      // extract 16 bytes for IPv6 address
      return StunMessageUtils.extractByteSequenceAsHex(valueBytes, 4, 16, true);
    } else {
      throw new StunParseException("invalid address Family: " + addressFamily);
    }
  }
  
  private void printBytes() {
    System.out.println("attribute contents");
    for (int i = 0; i < valueBytes.length; i++) {
      System.out.println(i + ": " + (valueBytes[i] & StunMessageUtils.MASK));
    }
  }
  
  protected boolean isValid() {
    if (valueBytes[0] != 0) {
      return false;
    }
    if (addressFamily == IPV4_FAMILY_CODE && valueBytes.length == IPV4_ATTRIBUTE_SIZE) {
      return true;
    } else if (addressFamily == IPV6_FAMILY_CODE && valueBytes.length == IPV6_ATTRIBUTE_SIZE) {
      return true;
    }
    
    return false;
  }
  
}