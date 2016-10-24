package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;

public class PriorityStunAttributeValue extends StunAttributeValue {

  private static final int PRIORITY_LENGTH_BYTES = 4;
  private long priority = 0;

  public PriorityStunAttributeValue(byte[] value) throws StunAttributeValueParseException {
    super(value);
  }

  protected boolean isValid() {
    return value.length == PRIORITY_LENGTH_BYTES;
  }

  protected void parseValueBytes() throws StunAttributeValueParseException {
    for (byte component : value) {
      priority |= (component & StunMessageUtils.MASK);
      priority <<= 8;
    }
  }
}
