package com.hal.stun.message.attribute.value;

import org.junit.Test;
import org.junit.Assert;

public class StunAttributeValueTest {

  @Test
  public void testGetPaddedBytes() throws Exception {
    byte[] testBytes = {
      (byte) 1,
      (byte) 2,
      (byte) 3,
      (byte) 4,
      (byte) 5
    };
    StunAttributeValue value = new TestStunAttributeValue(testBytes);
    byte[] paddedBytes = value.getPaddedBytes();

    for (int i = 0; i < testBytes.length; i++) {
      Assert.assertEquals(testBytes[i], paddedBytes[i]);
    }
    for (int i = 5; i < 8; i++) {
      Assert.assertEquals(0, paddedBytes[i]);
    }
  }

  @Test(expected = StunAttributeValueParseException.class)
  public void testInvalidStunAttributeValue() throws StunAttributeValueParseException {
    new InvalidTestStunAttributeValue(new byte[0]);
  }

  private static class TestStunAttributeValue extends StunAttributeValue {
    public TestStunAttributeValue(byte[] value) throws StunAttributeValueParseException {
      super(value);
    }

    protected void parseValueBytes() throws StunAttributeValueParseException {
    }

    protected boolean isValid() {
      return true;
    }
  }

  private static class InvalidTestStunAttributeValue extends StunAttributeValue {
    public InvalidTestStunAttributeValue(byte[] value) throws StunAttributeValueParseException {
      super(value);
    }

    protected void parseValueBytes() throws StunAttributeValueParseException {
    }

    protected boolean isValid() {
      return false;
    }
  }

}
