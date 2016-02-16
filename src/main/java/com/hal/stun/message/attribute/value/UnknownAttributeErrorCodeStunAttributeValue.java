package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunParseException;

public class UnknownAttributeErrorCodeStunAttributeValue extends ErrorCodeStunAttributeValue {
  public UnknownAttributeErrorCodeStunAttributeValue(String reason) {
    super(420, reason);
  }
}
