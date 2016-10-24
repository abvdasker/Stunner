package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;

public class UnknownAttributesAttributeValue extends StunAttributeValue {

  public UnknownAttributesAttributeValue(byte[] value) throws StunAttributeValueParseException {
    super(value);
  }

  public UnknownAttributesAttributeValue(short attributeType) throws StunAttributeValueParseException {
    super(StunMessageUtils.toBytes(attributeType));
  }

  protected boolean isValid() {
    return value.length % 2 == 0;
  }

  protected void parseValueBytes() throws StunAttributeValueParseException {
  }

}
