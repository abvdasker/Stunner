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
  protected static final byte IPV4_FAMILY_CODE = (byte) 0x01;
  protected static final byte IPV6_FAMILY_CODE = (byte) 0x02;

  protected static final int IPV4_ATTRIBUTE_SIZE = 8;
  protected static final int IPV6_ATTRIBUTE_SIZE = 20;
  
  private byte addressFamily;
  private short port;
  private InetAddress address;
  
  public MappedAddressStunAttributeValue(byte[] value) throws StunParseException {
    super(value);
  }

  public MappedAddressStunAttributeValue() throws StunParseException {
    super(null);
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
    addressFamily = value[1];
    port = parsePort();

    byte[] addressBytes = Arrays.copyOfRange(value, 4, value.length);
    try {
      address = InetAddress.getByAddress(addressBytes);
    } catch (UnknownHostException exception) {
      throw new StunParseException("could not parse internet address", exception);
    }
  }
  
  private short parsePort() {
    short thisPort = 0;
    thisPort = value[2];
    thisPort <<= 8;
    thisPort |= (value[3] & StunMessageUtils.MASK);
    return thisPort;
  }
  
  private void printBytes() {
    System.out.println("attribute contents");
    for (int i = 0; i < value.length; i++) {
      System.out.println(i + ": " + (value[i] & StunMessageUtils.MASK));
    }
  }
  
  protected boolean isValid() {
    if (value[0] != 0) {
      return false;
    }
    if (addressFamily == IPV4_FAMILY_CODE && value.length == IPV4_ATTRIBUTE_SIZE) {
      return true;
    } else if (addressFamily == IPV6_FAMILY_CODE && value.length == IPV6_ATTRIBUTE_SIZE) {
      return true;
    }
    
    return false;
  }
  
}
