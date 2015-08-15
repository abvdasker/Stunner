package com.hal.stun.message.attribute;

public enum AttributeType {
  
  // 16-bit attribute type
  MAPPED_ADDRESS((short) 0x0001),
  XOR_MAPPED_ADDRESS((short) 0x0020);
  
  private short type;
  AttributeType(short type) {
    this.type = type;
  }
  
  public short getTypeBytes() {
    return type;
  }
  
  public static AttributeType fromBytes(short type) throws UnrecognizedAttributeTypeException {
    for (AttributeType attributeType : AttributeType.values()) {
      if (attributeType.getTypeByte() == type) {
        return attributeType;
      }
    }
    
    // TODO: should probably have its own error
    throw new UnrecognizedAttributeTypeException(type);
  }
  
}