package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunParseException;

public class FingerprintStunAttributeValue extends StunAttributeValue {

  // TODO: implement
  public FingerprintStunAttributeValue(String valueHex) throws StunParseException {
    super(valueHex);
  }

  public boolean isValid() {
    return true;
  }

  public void parseValueBytes() throws StunParseException {
  }
}
