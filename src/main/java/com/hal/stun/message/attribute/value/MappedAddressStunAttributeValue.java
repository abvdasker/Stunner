package com.hal.stun.message.attribute.value;

public class MappedAddressAttributeValue {
  
  7 bytes OR 19 bytes
  // 0x0001 - type;
  // 0x0000 - length;
  // value
  //   0x00 address family;
  //   0x0000 port;
  //   0x0000 0000 ipv4 address
  //     OR
  //   0x0000 0000 0000 0000 0000 0000 0000 0000 ipv6 address
  //
  private static final byte IPV4_FAMILY_CODE = (byte) 0x01;
  private static final byte IPV6_FAMILY_CODE = (byte) 0x02;
  
  private static final int IPV4_ATTRIBUTE_SIZE = 7;
  private static final int IPV6_ATTRIBUTE_SIZE = 19;
  
  private byte addressFamily;
  private short port;
  private String addressHex;
  
  public MappedAddressAttributeValue(String valueHex) {
    super(valueHex);
    parseValueBytes();
  }
  
  private parseValueBytes() {
    addressFamily = valueBytes[1];
    port = parsePort();
    addressHex = parseAddressHex();
  }
  
  private short parsePort() {
    int thisPort = 0;
    thisPort = valueBytes[2];
    thisPort <<= 8;
    thisPort |= valueBytes[3];
    return thisPort;
  }
  
  private String parseAddressHex() {
    
  }
  
  private boolean isValid() {
    if (addressFamily == IPV4_FAMILY_CODE && valueBytes.length == IPV4_ATTRIBUTE_SIZE) {
      return true;
    } else if (addressFamily == IPV6_FAMILY_CODE && valueBytes.length == IPV6_ATTRIBUTE_SIZE) {
      return true;
    }
    
    return false;
  }
  
}