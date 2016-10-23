package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunParseException;
import com.hal.stun.message.StunMessageUtils;

public class UsernameStunAttributeValue extends StunAttributeValue {

  private final static int MAXIMUM_USERNAME_LENGTH_BYTES = 512;

  private String username;

  public UsernameStunAttributeValue(byte[] value) throws StunParseException {
    super(value);
  }

  public String getUsername() {
    return username;
  }

  protected boolean isValid() {
    return value.length <= MAXIMUM_USERNAME_LENGTH_BYTES;
  }

  protected void parseValueBytes() throws StunParseException {
    username = new String(value);
  }

  public String toString() {
    StringBuffer usernameStringBuffer = new StringBuffer();
    usernameStringBuffer.append('\n');
    usernameStringBuffer.append(getUsername());
    usernameStringBuffer.append('\n');
    usernameStringBuffer.append(StunMessageUtils.formatByteArray(value));
    return usernameStringBuffer.toString();
  }
}
