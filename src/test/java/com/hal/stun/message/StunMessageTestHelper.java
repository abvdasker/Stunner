package com.hal.stun.message;

import com.hal.stun.message.StunMessageUtils;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class StunMessageTestHelper {

    public static void invokeWithPossibleParseException(Method method, StunHeader stunMessage, byte[] argument)
            throws IllegalAccessException, InvocationTargetException, StunParseException {
        try {
            method.invoke(stunMessage, (Object) argument);
        } catch (InvocationTargetException exception) {
            Throwable cause = exception.getCause();
            if (cause != null && cause instanceof StunParseException) {
                throw (StunParseException) cause;
            } else {
                throw exception;
            }
        }
    }

    public static byte[] convertArray(int[] array) {
        byte[] convertedArray = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            convertedArray[i] = (byte) (value & StunMessageUtils.MASK);
        }
        return convertedArray;
    }
}
