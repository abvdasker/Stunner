package com.hal.stun.message.attribute.value;

public class UnauthorizedErrorCodeStunAttributeValue extends ErrorCodeStunAttributeValue {
  public UnauthorizedErrorCodeStunAttributeValue(String reason) throws StunAttributeValueParseException {
    super(401, reason);
  }
}
