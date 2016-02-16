package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunParseException;

public class BadRequestErrorCodeStunAttributeValue extends ErrorCodeStunAttributeValue {
  public BadRequestErrorCodeStunAttributeValue(String reason) throws StunParseException {
    super(400, reason);
  }
}
