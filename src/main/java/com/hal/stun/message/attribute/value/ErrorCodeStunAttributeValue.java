package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunParseException; // TODO: Create attribute value-specific sublcass to avoid these imports

import java.util.Arrays;

public abstract class ErrorCodeStunAttributeValue extends StunAttributeValue {

  private static final int MAX_REASON_SIZE_BYTES = 763;

  private String reason;
  public ErrorCodeStunAttributeValue(byte[] value) throws StunParseException {
    super(value);
  }

  public String getReason() {
    return reason;
  }

  protected boolean isValid() {
    return reservedBitsAreZero() &&
      getReasonBytes().length <= MAX_REASON_SIZE_BYTES;
  }
  
  protected void parseValueBytes() throws StunParseException {
    reason = new String(getReasonBytes());
  }

  private boolean reservedBitsAreZero() {
    boolean topByteIsZero = value[0] == 0;
    boolean secondByteIsZero = value[1] == 0;
    int thirdByte = value[3];
    int thirdByteTopFiveBits = thirdByte >>> 3;
    boolean thirdByteTopFiveBitsAreZero = thirdByteTopFiveBits == 0;
    return topByteIsZero &&
      secondByteIsZero &&
      thirdByteTopFiveBitsAreZero;
  }

  private byte[] getReasonBytes() {
    return Arrays.copyOfRange(value, 5, value.length);
  }
}
