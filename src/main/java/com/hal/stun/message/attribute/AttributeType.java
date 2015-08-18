package com.hal.stun.message.attribute;

import com.hal.stun.message.attribute.value.MappedAddressStunAttributeValue;
import com.hal.stun.message.attribute.value.XORMappedAddressStunAttributeValue;

public enum AttributeType {
  
  // 16-bit attribute type
  MAPPED_ADDRESS((short) 0x0001, MappedAddressStunAttribute.class),
  XOR_MAPPED_ADDRESS((short) 0x0020, XORMappedAddressStunAttribute.class);
  
  private short type;
  private Class<? extends StunAttribute> attributeClass;
  private AttributeType(short type, Class<? extends StunAttribute> attributeClass) {
    this.type = type;
    this.attributeClass = attributeClass;
  }
  
  public short getTypeBytes() {
    return type;
  }
  
  public Class<? extends StunAttribute> getAttributeClass() {
    return attributeClass;
  }
  
  public static AttributeType fromBytes(short type) throws UnrecognizedAttributeTypeException {
    for (AttributeType attributeType : AttributeType.values()) {
      if (attributeType.getTypeBytes() == type) {
        return attributeType;
      }
    }
    
    // TODO: should probably have its own error
    throw new UnrecognizedAttributeTypeException(type);
  }
  
}