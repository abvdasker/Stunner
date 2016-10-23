package com.hal.stun.message.attribute;

import com.hal.stun.message.StunParseException;
import com.hal.stun.message.attribute.value.StunAttributeValue;
import com.hal.stun.message.attribute.value.MappedAddressStunAttributeValue;
import com.hal.stun.message.attribute.value.XORMappedAddressStunAttributeValue;
import com.hal.stun.message.attribute.value.FingerprintStunAttributeValue;
import com.hal.stun.message.attribute.value.SoftwareStunAttributeValue;
import com.hal.stun.message.attribute.value.PriorityStunAttributeValue;
import com.hal.stun.message.attribute.value.ICEControlledStunAttributeValue;
import com.hal.stun.message.attribute.value.UsernameStunAttributeValue;
import com.hal.stun.message.attribute.value.MessageIntegrityStunAttributeValue;
import com.hal.stun.message.attribute.value.ErrorCodeStunAttributeValue;
import com.hal.stun.message.attribute.value.UnknownAttributesAttributeValue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum AttributeType {

  // 16-bit attribute type

  // required
  XOR_MAPPED_ADDRESS ((short) 0x0020, XORMappedAddressStunAttributeValue.class),
  FINGERPRINT        ((short) 0x8028, FingerprintStunAttributeValue.class),

  // optional
  SOFTWARE           ((short) 0x8022, SoftwareStunAttributeValue.class),
  PRIORITY           ((short) 0x0024, PriorityStunAttributeValue.class),
  ICE_CONTROLLED     ((short) 0x8029, ICEControlledStunAttributeValue.class),
  USERNAME           ((short) 0x0006, UsernameStunAttributeValue.class),
  MESSAGE_INTEGRITY  ((short) 0x0008, MessageIntegrityStunAttributeValue.class),
  MAPPED_ADDRESS     ((short) 0x0001, MappedAddressStunAttributeValue.class),
  ERROR_CODE         ((short) 0x0009, ErrorCodeStunAttributeValue.class),
  UNKNOWN_ATTRIBUTES ((short) 0x000A, UnknownAttributesAttributeValue.class);

  private short type;
  private Class<? extends StunAttributeValue> attributeValueClass;
  private AttributeType(short type, Class<? extends StunAttributeValue> attributeValueClass) {
    this.type = type;
    this.attributeValueClass = attributeValueClass;
  }

  public short getTypeBytes() {
    return type;
  }

  public StunAttributeValue buildAttributeValue(byte[] value)
      throws StunParseException {
    try {
      Constructor<? extends StunAttributeValue> constructor = attributeValueClass.getConstructor(byte[].class);
      return constructor.newInstance(value);
    } catch (NoSuchMethodException | IllegalAccessException exception) {
      throw new RuntimeException(exception);
    } catch (InstantiationException | InvocationTargetException exception) {
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

    throw new UnrecognizedAttributeTypeException(type);
  }

}
