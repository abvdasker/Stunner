package com.hal.stun.message.attribute;

import com.hal.stun.message.StunParseException;
import com.hal.stun.message.attribute.value.StunAttributeValue;
import com.hal.stun.message.attribute.value.XORMappedAddressStunAttributeValue;

public class XORMappedAddressStunAttribute extends StunAttribute {
  
  public XORMappedAddressStunAttribute(int length, String valueHex) throws StunParseException {
    super(AttributeType.XOR_MAPPED_ADDRESS, length, valueHex);
  }
  
  protected StunAttributeValue parseValueHex(String valueHex) throws StunParseException {
    return new XORMappedAddressStunAttributeValue(valueHex);
  }
  
}