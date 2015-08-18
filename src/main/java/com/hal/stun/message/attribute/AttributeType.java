package com.hal.stun.message.attribute;

import com.hal.stun.message.attribute.value.StunAttributeValue;
import com.hal.stun.message.attribute.value.MappedAddressStunAttributeValue;
import com.hal.stun.message.attribute.value.XORMappedAddressStunAttributeValue;

public enum AttributeType {
  
  // 16-bit attribute type
  MAPPED_ADDRESS((short) 0x0001, MappedAddressStunAttributeValue.class),
  XOR_MAPPED_ADDRESS((short) 0x0020, XORMappedAddressStunAttributeValue.class);
  
  private short type;
  private Class<? extends StunAttributeValue> attributeValueClass;
  private AttributeType(short type, Class<? extends StunAttributeValue> attributeValueClass) {
    this.type = type;
    this.attributeValueClass = attributeValueClass;
  }
  
  public short getTypeBytes() {
    return type;
  }
  
  public Class<? extends StunAttributeValue> getAttributeValueClass() {
    return attributeValueClass;
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