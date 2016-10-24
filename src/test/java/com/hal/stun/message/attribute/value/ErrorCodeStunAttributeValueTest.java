package com.hal.stun.message.attribute.value;

import org.junit.Test;
import org.junit.Assert;

public class ErrorCodeStunAttributeValueTest {

  @Test
  public void testParseReason() throws Exception {
    String expectedReason = "failed because X";
    byte[] reasonBytes = expectedReason.getBytes();
    byte[] valueBytes = new byte[reasonBytes.length + 4];
    System.arraycopy(reasonBytes, 0, valueBytes, 4, reasonBytes.length);

    ErrorCodeStunAttributeValue value = new ErrorCodeStunAttributeValue(valueBytes);
    String reason = value.getReason();

    Assert.assertEquals("reason parsed from byte array matches",
                        expectedReason,
                        reason);
  }

  @Test
  public void testParseErrorCode() throws Exception {
    byte[] valueBytes = {
      (byte) 0,
      (byte) 0,
      (byte) 4,
      (byte) 20
    };

    ErrorCodeStunAttributeValue value = new ErrorCodeStunAttributeValue(valueBytes);
    int errorCode = value.getErrorCode();

    Assert.assertEquals("parsed error code from byte array matches",
                        420,
                        errorCode);
  }

  @Test(expected = StunAttributeValueParseException.class)
  public void testInvalidReservedBitsNonZero() throws StunAttributeValueParseException {
    byte[] valueBytes = {
      (byte) 0,
      (byte) 0,
      (byte) 20,
      (byte) 0
    };

    new ErrorCodeStunAttributeValue(valueBytes);

    Assert.fail("should raise a parsing exception but did not");
  }

  @Test(expected = StunAttributeValueParseException.class)
  public void testInvalidReasonTooLong() throws StunAttributeValueParseException {
    byte[] valueBytes = new byte[ErrorCodeStunAttributeValue.MAX_REASON_SIZE_BYTES + 4 + 1];
    new ErrorCodeStunAttributeValue(valueBytes);
    Assert.fail("should raise a parsing exception but did not");
  }

}
