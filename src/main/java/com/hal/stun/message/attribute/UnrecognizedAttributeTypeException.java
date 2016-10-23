package com.hal.stun.message.attribute;

import com.hal.stun.message.StunParseException;
import com.hal.stun.message.StunMessageUtils;
import javax.xml.bind.DatatypeConverter;

public class UnrecognizedAttributeTypeException extends StunParseException {

  public UnrecognizedAttributeTypeException(short type) {
    super("Unrecognized attribute type: " + convertType(type));
  }

  private static String convertType(short type) {
    byte[] typeBytes = new byte[2];
    typeBytes[0] = (byte) (type >>> 8);
    typeBytes[1] = (byte) type;
    return DatatypeConverter.printHexBinary(typeBytes);
  }

}
