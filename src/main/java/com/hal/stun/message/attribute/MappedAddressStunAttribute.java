package com.hal.stun.message.attribute;

public class MappedAddressStunAttribute extends StunAttribute {

  private byte addressFamily;
  private short port;
  private String addressHex;
    
  public MappedAddressStunAttribute(byte addressFamily, short port, String addressHex) {
    super(AttributeType.MAPPED_ADDRESS, attributeLength(addressFamily, port, addressHex), )
    this.addressFamily
  }
  
  private static 
  
}