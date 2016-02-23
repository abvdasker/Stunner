package com.hal.stun.message.attribute.value;

import com.hal.stun.client.data.ClientTestData;

import org.junit.Test;
import org.junit.Assert;
import java.util.Arrays;

import com.hal.stun.message.StunParseException;

public class SoftwareStunAttributeValueTest {

  private static final byte[] SOFTWARE_ATTRIBUTE_VALUE
    = Arrays.copyOfRange(ClientTestData.BASIC_REQUEST_IPV4, 24, 41);
  private static final String MESSAGE_TOO_LONG
    = "irEgQIne11fRWvb9wGuEKYJtxruiLp5mTnxS4KZ22w6oerHUZ46IFjwXe2lOBY" +
    "hWfNB4Elb9DD4DHLMzykXTlUKkSOpB2an39NONzfHGyQ1yjonpE08Y5N4zyYmtvu4R";

  @Test
  public void testInitialize() throws Exception {
    new SoftwareStunAttributeValue(SOFTWARE_ATTRIBUTE_VALUE);
  }

  @Test
  public void testGetValue() throws Exception {
    new SoftwareStunAttributeValue(SOFTWARE_ATTRIBUTE_VALUE);
  }

  @Test(expected = StunParseException.class)
  public void testMessageIsTooLong() throws Exception {
    new SoftwareStunAttributeValue(MESSAGE_TOO_LONG.getBytes());
  }
}
