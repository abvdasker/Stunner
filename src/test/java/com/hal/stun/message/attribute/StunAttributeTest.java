package com.hal.stun.message.attribute;

import com.hal.stun.message.StunParseException;

import org.junit.Test;
import org.junit.Assert;

public class StunAttributeTest {

  private static final String ATTRIBUTE_VALUE_HEX = "000107d07f000001";
  
  @Test
  public void testInitializeStunAttribute() throws Exception {
    new StunAttribute(AttributeType.MAPPED_ADDRESS, 8, ATTRIBUTE_VALUE_HEX);
  }

  @Test(expected = StunParseException.class)
  public void testInitializeStunAttributeSizeMismatch() throws StunParseException, Exception {
    new StunAttribute(AttributeType.MAPPED_ADDRESS, 7, ATTRIBUTE_VALUE_HEX);
  }
  
}
