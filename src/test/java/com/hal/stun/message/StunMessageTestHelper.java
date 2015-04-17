package com.hal.stun.message;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class StunMessageTestHelper {
  
  public static void invokeWithPossibleParseException(Method method, StunHeader stunMessage, byte[] argument) 
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