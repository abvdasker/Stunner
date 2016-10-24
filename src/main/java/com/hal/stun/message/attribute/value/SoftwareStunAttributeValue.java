package com.hal.stun.message.attribute.value;

import java.io.UnsupportedEncodingException;

public class SoftwareStunAttributeValue extends StunAttributeValue {

  private static final String MESSAGE_ENCODING = "UTF-8";
  private static final int MAX_MESSAGE_LENGTH = 127;
  private String message;

  public SoftwareStunAttributeValue(byte[] value) throws StunAttributeValueParseException {
    super(value);
  }

  public SoftwareStunAttributeValue(String softwareName) throws StunAttributeValueParseException {
    super(buildValueBytes(softwareName));
  }

  protected void parseValueBytes() throws StunAttributeValueParseException {
    try {
      message = new String(value, MESSAGE_ENCODING);
    } catch (UnsupportedEncodingException exception) {
      throw new RuntimeException(exception);
    }
  }

  protected boolean isValid() {
    return message.length() <= MAX_MESSAGE_LENGTH;
  }

  private static byte[] buildValueBytes(String softwareName) {
    return softwareName.getBytes();
  }

  public String toString() {
    return message;
  }
}
