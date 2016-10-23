package com.hal.stun.message;

import org.junit.Test;
import org.junit.Assert;

public class StunMessageUtilsTest {
  
  @Test
  public void testExtractByteSequence() {
    byte[] bytes = new byte[8];
    int expectedByteSequence = 0x01ff01;
    bytes[0] = (byte) 0xd3;
    
    bytes[1] = (byte) 1;
    bytes[2] = (byte) 0xff;
    bytes[3] = (byte) 1;
    
    // extra stuff
    bytes[4] = (byte) 0xfa;
    bytes[7] = (byte) 0x7c;
    
    int actualByteSequence = StunMessageUtils.extractByteSequence(bytes, 1, 3);
    Assert.assertEquals("extracted byte sequence matches that written", expectedByteSequence, actualByteSequence);
  }
  
  @Test(expected = RuntimeException.class)
  public void testExtractByteSequenceLargeThan4Bytes() {
    byte[] bytes = new byte[8];
    StunMessageUtils.extractByteSequence(bytes, 0, 5);
  }
  
  @Test
  public void testExtractByteSequenceAsHex() {
    byte[] bytes = new byte[8];
    String expectedByteSequence = "01ff01";
    bytes[0] = (byte) 0xd3;
    
    bytes[1] = (byte) 1;
    bytes[2] = (byte) 0xff;
    bytes[3] = (byte) 1;
    
    // extra stuff
    bytes[4] = (byte) 0xfa;
    bytes[7] = (byte) 0x7c;
    
    String actualByteSequence = StunMessageUtils.extractByteSequenceAsHex(bytes, 1, 3, true);
    Assert.assertEquals("extracted byte sequence matches that written", expectedByteSequence, actualByteSequence);
  }
  
  @Test
  public void testConvertHexToByteArray() {
    String hex = "010203";
    byte[] result = StunMessageUtils.convertHexToByteArray(hex);
    Assert.assertEquals(result[0], (byte) 1);
    Assert.assertEquals(result[1], (byte) 2);
    Assert.assertEquals(result[2], (byte) 3);
    
    hex = "000107d0";
    result = StunMessageUtils.convertHexToByteArray(hex);
    Assert.assertEquals(result[0], (byte) 0);
    Assert.assertEquals(result[1], (byte) 1);
    Assert.assertEquals(result[2], (byte) 7);
    Assert.assertEquals(result[3], (byte) 208);
  }

  @Test
  public void testToShort() {
    byte[] bytes = {
      (byte) 0xFA,
      (byte) 0x0A
    };

    short result = StunMessageUtils.toShort(bytes);

    Assert.assertEquals((short) 0xFA0A, result);
  }

  @Test
  public void testToBytes() {
    short bytePair = (short) 0xFA0A;
    byte[] result = StunMessageUtils.toBytes(bytePair);

    byte[] expectedResult = {
      (byte) 0xFA,
      (byte) 0x0A
    };

    Assert.assertArrayEquals(expectedResult,
                             result);
  }
  
}
