package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunParseException;
import com.hal.stun.client.data.ClientTestData;

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

  @Test
  public void testUpdate() throws StunParseException {
    byte[] dummyBytes = new byte[FingerprintStunAttributeValue.VALUE_SIZE_BYTES];
    FingerprintStunAttributeValue value = new FingerprintStunAttributeValue(dummyBytes);

    byte[] messageContent = ClientTestData.getMessageNoFingerprintV6();
    value.update(messageContent);

    byte[] fingerprintValue = value.getBytes();
    byte[] expectedFingerprintValue = ClientTestData.getFingerprintValueV6();

    Assert.assertArrayEquals("fingerprint value should match the test value",
                             expectedFingerprintValue,
                             fingerprintValue);
  }
}
