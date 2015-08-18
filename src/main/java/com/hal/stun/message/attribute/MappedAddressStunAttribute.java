package com.hal.stun.message.attribute;

import com.hal.stun.message.StunParseException;
import com.hal.stun.message.attribute.value.StunAttributeValue;
import com.hal.stun.message.attribute.value.MappedAddressStunAttributeValue;

public class MappedAddressStunAttribute extends StunAttribute {
  
  public MappedAddressStunAttribute(int length, String valueHex) throws StunParseException {
    super(AttributeType.MAPPED_ADDRESS, length, valueHex);
  }
  
  protected StunAttributeValue parseValueHex(String valueHex) throws StunParseException {
    return new MappedAddressStunAttributeValue(valueHex);
  }
  
}