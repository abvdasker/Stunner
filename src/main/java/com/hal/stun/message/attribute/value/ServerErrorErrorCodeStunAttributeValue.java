package com.hal.stun.message.attribute.value;

public class ServerErrorErrorCodeStunAttributeValue extends ErrorCodeStunAttributeValue {
  public ServerErrorErrorCodeStunAttributeValue(String reason) throws StunAttributeValueParseException {
    super(500, reason);
  }
}
