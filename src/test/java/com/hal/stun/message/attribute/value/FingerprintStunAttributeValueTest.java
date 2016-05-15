package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunParseException;

import org.junit.Test;
import org.junit.Assert;

public class FingerprintStunAttributeValueTest {
  @Test
  public void testValidation() throws StunParseException {
    byte[] attributeValueBytes = new byte[FingerprintStunAttributeValue.VALUE_SIZE_BYTES];
    StunAttributeValue value = new FingerprintStunAttributeValue(attributeValueBytes);
    Assert.assertTrue("should be of valid length", value.isValid());
  }

  @Test(expected = StunParseException.class)
  public void testValidationTooLong() throws StunParseException {
    byte[] attributeValueBytes = new byte[FingerprintStunAttributeValue.VALUE_SIZE_BYTES + 1];
    StunAttributeValue value = new FingerprintStunAttributeValue(attributeValueBytes);
    Assert.fail();
  }
}
