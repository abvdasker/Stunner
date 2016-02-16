package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunParseException;

public class ServerErrorErrorCodeStunAttributeValue extends ErrorCodeStunAttributeValue {
  public ServerErrorErrorCodeStunAttributeValue(String reason) {
    super(500, reason);
  }
}
