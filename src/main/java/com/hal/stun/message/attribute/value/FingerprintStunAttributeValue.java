package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunParseException;

public class FingerprintStunAttributeValue extends StunAttributeValue {

  // TODO: implement
  public FingerprintStunAttributeValue(byte[] value) throws StunParseException {
    super(value);
  }

  public boolean isValid() {
    return true;
  }

  public void parseValueBytes() throws StunParseException {
  }
}
