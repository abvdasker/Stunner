package com.hal.stun.message.attribute;

public class UnrecognizedAttributeTypeException extends Exception {
  
  public UnrecognizedAttributeTypeException(short type) {
    super("Unrecognized attribute type: " + (type & 0xffff));
  }
  
}
