package com.hal.stun.message;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import org.junit.BeforeClass;
import org.junit.Assert;
import org.junit.Test;

import static com.hal.stun.message.StunMessageUtils.MASK;

public class StunHeaderTest {

  private static Method verifyFirstByteMethod;
  private static Method verifyMagicCookieMethod;
  private static Method getMessageClassBitsMethod;
  private static Method parseMessageLengthMethod;
  private static Method getMessageMethodMethod;

  @BeforeClass
  public static void beforeAll() throws NoSuchMethodException {
    
    verifyFirstByteMethod = StunHeader.class.getDeclaredMethod("verifyFirstByte", byte[].class);
    verifyFirstByteMethod.setAccessible(true);
    
    verifyMagicCookieMethod = StunHeader.class.getDeclaredMethod("verifyMagicCookie", byte[].class);
    verifyMagicCookieMethod.setAccessible(true);
    
    getMessageClassBitsMethod = StunHeader.class.getDeclaredMethod("getMessageClassBits", byte[].class);
    getMessageClassBitsMethod.setAccessible(true);

    parseMessageLengthMethod = StunHeader.class.getDeclaredMethod("parseMessageLength", byte[].class);
    parseMessageLengthMethod.setAccessible(true);

    getMessageMethodMethod = StunHeader.class.getDeclaredMethod("getMessageMethod", byte[].class);
    getMessageMethodMethod.setAccessible(true);
    
  }
  
  @Test
  public void testVerifyFirstByte() 
      throws StunParseException, IllegalAccessException, InvocationTargetException {
    byte[] headerBytes = new byte[StunHeader.HEADER_SIZE];
    StunMessageTestHelper.invokeWithPossibleParseException(verifyFirstByteMethod, null, headerBytes);
  }
  
  @Test(expected = StunParseException.class)
  public void testVerifyFirstByteWithBitSet() 
      throws StunParseException, IllegalAccessException, InvocationTargetException {
    byte[] headerBytes = new byte[StunHeader.HEADER_SIZE];
    headerBytes[0] = (byte) 0b10000000;
    StunMessageTestHelper.invokeWithPossibleParseException(verifyFirstByteMethod, null, headerBytes);
  }

  @Test
  public void testGetMessageClassBits() throws IllegalAccessException, InvocationTargetException {
    byte[] headerBytes = new byte[StunHeader.HEADER_SIZE];
    byte expectedClassBits = MessageClass.ERROR.getClassBits();
    headerBytes[0] = (byte) 0xff;
    headerBytes[1] = (byte) 0xff;
    byte actualClassBits = (byte) getMessageClassBitsMethod.invoke(null, headerBytes);
    Assert.assertEquals("message class retrieved from header matches that set in header",
      expectedClassBits, actualClassBits);
  }
  
  @Test
  public void testVerifyMagicCookie()
      throws StunParseException, IllegalAccessException, InvocationTargetException {
    byte[] headerBytes = new byte[StunHeader.HEADER_SIZE];
    addValidMagicCookie(headerBytes);
    StunMessageTestHelper.invokeWithPossibleParseException(verifyMagicCookieMethod, null, headerBytes);
  }

  @Test(expected = StunParseException.class)
  public void testVerifyInvalidMagicCookie()
      throws StunParseException, IllegalAccessException, InvocationTargetException {
    byte[] headerBytes = new byte[StunHeader.HEADER_SIZE];
    int magicCookie = MagicCookie.VALUE;
    addValidMagicCookie(headerBytes);
    headerBytes[7] = (byte) ((magicCookie & MASK) + 1);

    StunMessageTestHelper.invokeWithPossibleParseException(verifyMagicCookieMethod, null, headerBytes);
  }
  

  @Test
  public void testGetMessageLength() throws IllegalAccessException, InvocationTargetException {
    byte[] headerBytes = new byte[StunHeader.HEADER_SIZE];
    int expectedMessageLength = 0xffff;
    expectedMessageLength >>>= 2;
    expectedMessageLength <<= 2;
    headerBytes[2] = (byte) 0xff;
    headerBytes[3] = (byte) (0xff << 2); // clear lower 2 bits
    int actualMessageLength = (int) parseMessageLengthMethod.invoke(null, headerBytes);
    Assert.assertEquals("message length should match that set in header",
      expectedMessageLength, actualMessageLength);
  }
  
  @Test(expected = StunParseException.class)
  public void testGetInvalidMessageLength()
      throws IllegalAccessException, InvocationTargetException, StunParseException {
    byte[] headerBytes = new byte[StunHeader.HEADER_SIZE];
    int expectedMessageLength = 0xffff;
    headerBytes[2] = (byte) 0xff;
    headerBytes[3] = (byte) 0xff; // clear lower 2 bits
    StunMessageTestHelper.invokeWithPossibleParseException(parseMessageLengthMethod, null, headerBytes);
  }
  
  @Test
  public void testGetMessageMethod() throws IllegalAccessException, InvocationTargetException {
    byte[] headerBytes = new byte[StunHeader.HEADER_SIZE];
    short expectedMessageMethod = (short) 0xff;
    headerBytes[0] = (byte) 0b11;
    headerBytes[1] = (byte) 0xff;
    short actualMessageMethod = (short) getMessageMethodMethod.invoke(null, headerBytes);
    Assert.assertEquals(
      "retrieved method should be the same as the input method with the lowest bit of the upper method",
      expectedMessageMethod, actualMessageMethod);
  }

  private static byte[] addValidMagicCookie(byte[] headerBytes) {
    int magicCookie = MagicCookie.VALUE;
    headerBytes[4] = (byte) ((magicCookie >>> 3*8) & MASK);
    headerBytes[5] = (byte) ((magicCookie >>> 2*8) & MASK);
    headerBytes[6] = (byte) ((magicCookie >>> 1*8) & MASK);
    headerBytes[7] = (byte) (magicCookie & MASK);
    return headerBytes;
  }

}
