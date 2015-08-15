package com.hal.stun.message.attribute;

public class UnrecognizedAttributeTypeException extends Exception {
  
  public UnrecognizedAttributeTypeException(byte type) {
    super("Unrecognized attribute type: " + type);
  }
  
}
