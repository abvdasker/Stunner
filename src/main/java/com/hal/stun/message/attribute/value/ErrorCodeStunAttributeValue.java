package com.hal.stun.message.attribute.value;

import java.util.Arrays;

import com.hal.stun.message.StunParseException; // TODO: Create attribute value-specific sublcass to avoid these imports

public abstract class ErrorCodeStunAttributeValue extends StunAttributeValue {

  private static final int MAX_REASON_SIZE_BYTES = 763;

  private byte errorClass;
  private byte number;
  private String reason;
  public ErrorCodeStunAttributeValue(byte[] value) throws StunParseException {
    super(value);
  }

  protected ErrorCodeStunAttributeValue(int errorCode, String reason) {
    super(buildErrorBytes(erorrCode, reason));
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

  private static byte[] buildErrorBytes(int errorCode, String reason) {
    byte errorClass = (byte) (errorCode / 100);
    byte number = (byte) (errorCode % 100);
    byte[] reasonBytes = reason.getBytes();
    byte[] result = new byte[4 + reasonBytes.length];
    result[2] = errorClass;
    result[3] = number;
    System.arraycopy(reasonBytes, 0, result, 4, reason.length);
    return result;
  }
}
