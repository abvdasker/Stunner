package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunParseException;
import com.hal.stun.message.StunMessageUtils;

public class UnknownAttributesAttributeValue extends StunAttributeValue {

  public UnknownAttributesAttributeValue(byte[] value) throws StunParseException {
    super(value);
  }

  public UnknownAttributesAttributeValue(short attributeType) throws StunParseException {
    super(StunMessageUtils.toBytes(attributeType));
  }

  protected boolean isValid() {
    return value.length % 2 == 0;
  }

  protected void parseValueBytes() throws StunParseException {
  }

}
