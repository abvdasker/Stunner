package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunParseException;
import java.math.BigInteger;

public class ICEControlledStunAttributeValue extends StunAttributeValue {

  private BigInteger tieBreaker;

  public ICEControlledStunAttributeValue(byte[] value) throws StunParseException {
    super(value);
  }

  protected boolean isValid() {
    return value.length == 8;
  }

  protected void parseValueBytes() throws StunParseException {
    tieBreaker = new BigInteger(value);
  }
}
