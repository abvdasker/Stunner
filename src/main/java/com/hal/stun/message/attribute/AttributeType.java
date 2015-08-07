package com.hal.stun.message;

public enum AttributeType {
  
  MAPPED_ADDRESS((byte) 0x0001),
  XOR_MAPPED_ADDRESS((byte) 0x0020);
  
  private byte type;
  AttributeType(byte type) {
    this.type = type;
  }
  
  public byte getTypeBytes() {
    return type;
  }
  
  public static AttributeType fromByte(byte type) {
    for (AttributeType attributeType : AttributeType.values()) {
      if (attributeType.getTypeBytes() == type) {
        return attributeType;
      }
    }
    
    // TODO: should probably have its own error
    throw new RuntimeException("should never reach here. Invalid Attribute type specified");
  }
  
}