package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;
import com.hal.stun.message.StunParseException;

public class PriorityStunAttributeValue extends StunAttributeValue {

  private long priority = 0;

  public PriorityStunAttributeValue(byte[] value) throws StunParseException {
    super(value);
  }

  protected boolean isValid() {
    return value.length == 4;
  }

  protected void parseValueBytes() throws StunParseException {
    for (byte component : value) {
      priority |= (component & StunMessageUtils.MASK);
      priority <<= 8;
    }
  }
}
