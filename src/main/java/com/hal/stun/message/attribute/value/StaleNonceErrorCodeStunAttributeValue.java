package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunParseException;

public class StaleNonceErrorCodeStunAttributeValue extends ErrorCodeStunAttributeValue {
  public StaleNonceErrorCodeStunAttributeValue(String reason) throws StunParseException {
    super(438, reason);
  }
}
