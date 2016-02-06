package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunParseException;
import java.io.UnsupportedEncodingException;


public class SoftwareStunAttributeValue extends StunAttributeValue {

  private static final String MESSAGE_ENCODING = "UTF-8";
  private static final int MAX_MESSAGE_LENGTH = 128;
  private String message;

  public SoftwareStunAttributeValue(byte[] value) throws StunParseException {
    super(value);
  }

  public String getMessage() {
    return message;
  }

  protected void parseValueBytes() throws StunParseException {
    try {
      message = new String(value, MESSAGE_ENCODING);
    } catch (UnsupportedEncodingException exception) {
      throw new RuntimeException(exception);
    }
  }

  protected boolean isValid() {
    return message.length() < MAX_MESSAGE_LENGTH;
  }
}
