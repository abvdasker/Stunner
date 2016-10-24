package com.hal.stun.message.attribute.value;

import com.hal.stun.message.attribute.StunAttributeParseException;

public class StunAttributeValueParseException extends StunAttributeParseException {

  public StunAttributeValueParseException(String message) {
    super(message);
  }
  
  public StunAttributeValueParseException(String message, Exception exception) {
    super(message, exception);
  }

}
