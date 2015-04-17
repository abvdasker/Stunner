package com.hal.stun.message;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import org.junit.BeforeClass;
import org.junit.Assert;
import org.junit.Test;

public class StunMessageTest {

  private static Method getHeaderBytesMethod;
  private static Method getAttributesBytesMethod;
  
  @BeforeClass
  public static void beforeAll() throws NoSuchMethodException {
    getHeaderBytesMethod = StunMessage.class.getDeclaredMethod("getHeaderBytes", byte[].class);
    getHeaderBytesMethod.setAccessible(true);

    getAttributesBytesMethod = StunMessage.class.getDeclaredMethod("getAttributesBytes", byte[].class);
    getAttributesBytesMethod.setAccessible(true);
  }
  
  @Test
  public void testGetHeaderBytes() throws Exception {
    int messageSize = 50;
    byte[] testBytes = new byte[messageSize];
    testBytes[messageSize - 1] = 0b10;
    testBytes[StunHeader.HEADER_SIZE - 1] = 0b11;
    byte[] actualHeaderBytes = (byte[]) getHeaderBytesMethod.invoke(null, testBytes);
	
    byte[] expectedHeaderBytes = new byte[StunHeader.HEADER_SIZE];
    expectedHeaderBytes[19] = 0b11;
    Assert.assertEquals("header size should be " + StunHeader.HEADER_SIZE + " bytes", 
      StunHeader.HEADER_SIZE, actualHeaderBytes.length);
	
    Assert.assertArrayEquals("header byte arrays should match", 
      expectedHeaderBytes, actualHeaderBytes);
	
  }
  
  @Test(expected = StunParseException.class)
  public void testGetHeaderBytesTooFew() 
  throws StunParseException, IllegalAccessException, InvocationTargetException {
    byte[] testBytes = new byte[StunHeader.HEADER_SIZE - 1];

    StunMessageTestHelper.invokeWithPossibleParseException(getHeaderBytesMethod, null, testBytes);
  }
  
  
}