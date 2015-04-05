package com.hal.stun.message;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;


import org.junit.BeforeClass;
import org.junit.Assert;
import org.junit.Test;



public class StunMessageTest {

  private static Method getHeaderBytesMethod;
  private static Method parseHeaderMethod;
  private static Method getMessageMethodMethod;
  private static Method getMessageClassBitsMethod;
  private static Method getMessageLengthMethod;

  @BeforeClass
  public static void BeforeAll() throws NoSuchMethodException {
    getHeaderBytesMethod = StunMessage.class.getDeclaredMethod("getHeaderBytes", byte[].class);
    getHeaderBytesMethod.setAccessible(true);
    
    parseHeaderMethod = StunMessage.class.getDeclaredMethod("parseHeader", byte[].class);
    parseHeaderMethod.setAccessible(true);
    
    getMessageClassBitsMethod = StunMessage.class.getDeclaredMethod("getMessageClassBits", byte[].class);
    getMessageClassBitsMethod.setAccessible(true);
    
    getMessageMethodMethod = StunMessage.class.getDeclaredMethod("getMessageMethod", byte[].class);
    getMessageMethodMethod.setAccessible(true);
    
    getMessageLengthMethod = StunMessage.class.getDeclaredMethod("getMessageLength", byte[].class);
    getMessageLengthMethod.setAccessible(true);
  }

  @Test
  public void testGetHeaderBytes() throws Exception {
    int messageSize = 50;
    byte[] testBytes = new byte[messageSize];
    testBytes[messageSize - 1] = 0b10;
    testBytes[StunMessage.HEADER_SIZE - 1] = 0b11;
    byte[] actualHeaderBytes = (byte[]) getHeaderBytesMethod.invoke(null, testBytes);
	
    Field headerSizeConstant = StunMessage.class.getDeclaredField("HEADER_SIZE");
    headerSizeConstant.setAccessible(true);
    int expectedHeaderSize = (Integer) headerSizeConstant.get(null);
    byte[] expectedHeaderBytes = new byte[expectedHeaderSize];
    expectedHeaderBytes[19] = 0b11;
    Assert.assertEquals("header size should be " + expectedHeaderSize + " bytes", 
      expectedHeaderSize, actualHeaderBytes.length);
	
    Assert.assertArrayEquals("header byte arrays should match", 
      expectedHeaderBytes, actualHeaderBytes);
	
  }

  @Test(expected = StunParseException.class)
  public void testGetHeaderBytesTooFew() 
  throws StunParseException, IllegalAccessException, InvocationTargetException {
    byte[] testBytes = new byte[StunMessage.HEADER_SIZE - 1];

    invocationShouldCauseParseException(getHeaderBytesMethod, null, testBytes);
  }
  
  @Test(expected = StunParseException.class)
  public void testInitializeMessageTooSmall() throws StunParseException {
    byte[] testBytes = new byte[StunMessage.HEADER_SIZE - 1];
    new StunMessage(testBytes);
  }
  
  
  @Test(expected = StunParseException.class)
  public void testParseHeaderFirstTwoBitsSet() 
  throws StunParseException, IllegalAccessException, InvocationTargetException {
    byte[] messageBytes = new byte[50];
    messageBytes[1] = (byte) StunMessage.BINDING_METHOD;
    byte[] headerBytes = new byte[StunMessage.HEADER_SIZE];
    headerBytes[0] = (byte) 0b01000000;
    headerBytes[1] = (byte) StunMessage.BINDING_METHOD;
    StunMessage stunMessage = new StunMessage(messageBytes);
    invocationShouldCauseParseException(parseHeaderMethod, stunMessage, headerBytes);
  }

  @Test
  public void testGetMessageClassBits() throws IllegalAccessException, InvocationTargetException {
    byte[] headerBytes = new byte[StunMessage.HEADER_SIZE];
    byte expectedClassBits = MessageClass.ERROR.getClassBits();
    headerBytes[0] = Byte.MAX_VALUE;
    headerBytes[1] = Byte.MAX_VALUE;
    byte actualClassBits = (byte) getMessageClassBitsMethod.invoke(null, headerBytes);
    Assert.assertEquals("message class retrieved from header matches that set in header", 
      expectedClassBits, actualClassBits);
  }
  
  @Test
  public void testGetMessageLength() throws IllegalAccessException, InvocationTargetException {
    byte[] headerBytes = new byte[StunMessage.HEADER_SIZE];
    int expectedMessageLength = 0xffff;
    expectedMessageLength >>>= 2;
    expectedMessageLength <<= 2;
    headerBytes[2] = (byte) 0xff;
    headerBytes[3] = (byte) (0xff << 2); // clear lower 2 bits
    int actualMessageLength = (int) getMessageLengthMethod.invoke(null, headerBytes);
    Assert.assertEquals("message length should match that set in header", 
      expectedMessageLength, actualMessageLength);
  }

  @Test(expected = StunParseException.class)
  public void testGetInvalidMessageLength() 
  throws IllegalAccessException, InvocationTargetException, StunParseException {
    byte[] headerBytes = new byte[StunMessage.HEADER_SIZE];
    int expectedMessageLength = 0xffff;
    headerBytes[2] = (byte) 0xff;
    headerBytes[3] = (byte) 0xff; // clear lower 2 bits
    invocationShouldCauseParseException(getMessageLengthMethod, null, headerBytes);
  }
  
  private void invocationShouldCauseParseException(Method method, StunMessage stunMessage, byte[] argument) 
  throws IllegalAccessException, InvocationTargetException, StunParseException {
    try {
      method.invoke(stunMessage, argument);
    } catch (InvocationTargetException exception) {
      Throwable cause = exception.getCause();
      if (cause != null && cause instanceof StunParseException) {
        throw (StunParseException) cause;
      } else {
        throw exception;
      }
    }
  }

}