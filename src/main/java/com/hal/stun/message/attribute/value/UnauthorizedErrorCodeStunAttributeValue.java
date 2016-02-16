package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunParseException;

public class UnauthorizedErrorCodeStunAttributeValue extends ErrorCodeStunAttributeValue {
  public UnauthorizedErrorCodeStunAttributeValue(String reason) throws StunParseException {
    super(401, reason);
  }
}
