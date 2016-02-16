package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunParseException;

public class BadRequestErrorCodeStunAttributeValue extends ErrorCodeStunAttributeValue {
  public BadRequestErrorCodeStunAttributeValue(String reason) {
    super(400, reason);
  }
}
