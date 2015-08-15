package com.hal.stun.message.attribute;

public enum AttributeType {
  
  MAPPED_ADDRESS((byte) 0x0001),
  XOR_MAPPED_ADDRESS((byte) 0x0020);
  
  private byte type;
  AttributeType(byte type) {
    this.type = type;
  }
  
  public byte getTypeByte() {
    return type;
  }
  
  public static AttributeType fromByte(byte type) throws UnrecognizedAttributeTypeException {
    for (AttributeType attributeType : AttributeType.values()) {
      if (attributeType.getTypeByte() == type) {
        return attributeType;
      }
    }
    
    // TODO: should probably have its own error
    throw new UnrecognizedAttributeTypeException(type);
  }
  
}