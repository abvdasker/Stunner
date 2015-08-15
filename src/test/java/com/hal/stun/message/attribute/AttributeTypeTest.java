package com.hal.stun.message.attribute;

import org.junit.Test;
import org.junit.Assert;

public class AttributeTypeTest {
  
  @Test
  public void testInitializeAttributeType() throws Exception {
    AttributeType typeMappedAddress = AttributeType.fromByte((byte)0x0001);
    Assert.assertEquals("type should be MAPPED ADDRESS", AttributeType.MAPPED_ADDRESS, typeMappedAddress);
    AttributeType typeXORAddress = AttributeType.fromByte((byte)0x0020);
    Assert.assertEquals("type should be MAPPED ADDRESS", AttributeType.XOR_MAPPED_ADDRESS, typeXORAddress);
  }
  
  @Test(expected = UnrecognizedAttributeTypeException.class)
  public void testInitializeAttributeTypeMissingAttribute() 
  throws UnrecognizedAttributeTypeException {
    AttributeType.fromByte((byte) 0x0010);
  }
  
  @Test
  public void testGetTypeByte() throws Exception {
    byte expectedTypeByte = (byte) 0x0001;
    byte typeByte = AttributeType.MAPPED_ADDRESS.getTypeByte();
    Assert.assertEquals("should return the byte for this object", expectedTypeByte, typeByte);
  }
  
}