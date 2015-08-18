package com.hal.stun.message.attribute;

import com.hal.stun.message.StunParseException;
import com.hal.stun.message.attribute.value.StunAttributeValue;
import com.hal.stun.message.attribute.value.MappedAddressStunAttributeValue;
import com.hal.stun.message.attribute.value.XORMappedAddressStunAttributeValue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
  
  public StunAttributeValue buildAttributeValue(String valueHex)
      throws StunParseException {
    try {
      Constructor<? extends StunAttributeValue> constructor = attributeValueClass.getConstructor(String.class);
      return constructor.newInstance(valueHex);
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException exception) {
      throw new RuntimeException("could not instantiate class", exception);
    } catch (InvocationTargetException exception) {
      Throwable cause = exception.getCause();
      if (cause instanceof StunParseException) {
        throw (StunParseException) cause;
      } else {
        throw new RuntimeException(cause);
      }
    }
    
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