package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunParseException;

public class UnauthorizedErrorCodeStunAttributeValue extends ErrorCodeStunAttributeValue {
  public UnauthorizedErrorCodeStunAttributeValue(String reason) {
    super(401, reason);
  }
}
