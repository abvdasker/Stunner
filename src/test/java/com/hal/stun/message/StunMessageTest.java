package com.hal.stun.message;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;


import org.junit.BeforeClass;
import org.junit.Assert;
import org.junit.Test;



public class StunMessageTest {

    private static Method getHeaderBytesMethod;

    @BeforeClass
    public static void BeforeAll() throws NoSuchMethodException {
	getHeaderBytesMethod = StunMessage.class.getDeclaredMethod("getHeaderBytes", byte[].class);
	getHeaderBytesMethod.setAccessible(true);
    }

    @Test
    public void testGetHeaderBytes() throws Exception {
	byte[] testBytes = new byte[50];
	testBytes[49] = 0b10;
	testBytes[19] = 0b11;
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
	byte[] testBytes = new byte[19];

	try {
	    getHeaderBytesMethod.invoke(null, testBytes);
	} catch (InvocationTargetException exception) {
	    Throwable cause = exception.getCause();
	    if (cause != null && cause instanceof StunParseException) {
		throw (StunParseException) cause;
	    } else {
		throw exception;
	    }
	}
	
    }

    @Test(expected = StunParseException.class)
    public void testInitializeMessageTooSmall() throws StunParseException {
	byte[] testBytes = new byte[19];
	new StunMessage(testBytes);
    }

}