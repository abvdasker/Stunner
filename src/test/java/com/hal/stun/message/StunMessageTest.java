package com.hal.stun.message;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import javax.xml.bind.DatatypeConverter;

import com.hal.stun.message.StunMessageTestHelper;

import org.junit.BeforeClass;
import org.junit.Assert;
import org.junit.Test;

public class StunMessageTest {
  private static final String HEX_ENCODED_STUN_REQUEST = "0001000c2112A442115cfdfe3a1b4139e4fc98310020000800013e837f000001";

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
  
  @Test
  public void testInitializeStunMessage() throws Exception {
    new StunMessage(getTestStunRequest(), null);
  }
  
  private static byte[] getTestStunRequest() {
    return DatatypeConverter.parseHexBinary(HEX_ENCODED_STUN_REQUEST);
  }
}
