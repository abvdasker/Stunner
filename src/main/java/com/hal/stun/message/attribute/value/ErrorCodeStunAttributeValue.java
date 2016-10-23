package com.hal.stun.message.attribute.value;

import java.util.Arrays;

import com.hal.stun.message.StunMessageUtils;
import com.hal.stun.message.StunParseException; // TODO: Create attribute value-specific sublcass to avoid these imports

public class ErrorCodeStunAttributeValue extends StunAttributeValue {

  private static final int MAX_REASON_SIZE_BYTES = 763;

  private String reason;
  public ErrorCodeStunAttributeValue(byte[] value) throws StunParseException {
    super(value);
  }

  protected ErrorCodeStunAttributeValue(int errorCode, String reason) throws StunParseException {
    super(buildErrorBytes(errorCode, reason));
  }

  public String getReason() {
    return reason;
  }

  protected boolean isValid() {
    return value.length > 4 && reservedBitsAreZero() &&
      getReasonBytes().length <= MAX_REASON_SIZE_BYTES;
  }

  protected void parseValueBytes() throws StunParseException {
    reason = new String(getReasonBytes());
  }

  private boolean reservedBitsAreZero() {
    boolean topByteIsZero = value[0] == 0;
    boolean secondByteIsZero = value[1] == 0;
    int thirdByte = value[2];
    int thirdByteTopFiveBits = thirdByte >>> 3;
    boolean thirdByteTopFiveBitsAreZero = thirdByteTopFiveBits == 0;
    return topByteIsZero &&
      secondByteIsZero &&
      thirdByteTopFiveBitsAreZero;
  }

  private byte[] getReasonBytes() {
    return Arrays.copyOfRange(value, 4, value.length);
  }

  private byte getErrorClass() {
    return value[3];
  }

  private byte getNumber() {
    return value[4];
  }

  private static byte[] buildErrorBytes(int errorCode, String reason) {
    byte errorClass = (byte) (errorCode / 100);
    byte number = (byte) (errorCode % 100);
    byte[] reasonBytes = reason.getBytes();
    int resultLength = 4 + reasonBytes.length;
    byte[] result = new byte[resultLength];
    result[2] = errorClass;
    result[3] = number;
    System.arraycopy(reasonBytes, 0, result, 4, reasonBytes.length);
    return result;
  }

  public String toString() {
    return reason + " (" + getErrorCode() + ")";
  }

  private int getErrorCode() {
    int convertedErrorClass = (getErrorClass() & StunMessageUtils.MASK) * 100;
    int convertedNumber = (getNumber() & StunMessageUtils.MASK);
    return convertedErrorClass + convertedNumber;
  }
}
