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

  @Test
  public void testReasonTooLong() {
  }

}
