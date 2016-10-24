package com.hal.stun.message.attribute.value;

import java.math.BigInteger;

public class ICEControlledStunAttributeValue extends StunAttributeValue {

  private static final int TIE_BREAKER_LENGTH_BYTES = 8;
  private BigInteger tieBreaker;

  public ICEControlledStunAttributeValue(byte[] value) throws StunAttributeValueParseException {
    super(value);
  }

  protected boolean isValid() {
    return value.length == TIE_BREAKER_LENGTH_BYTES;
  }

  protected void parseValueBytes() throws StunAttributeValueParseException {
    tieBreaker = new BigInteger(value);
  }
}
