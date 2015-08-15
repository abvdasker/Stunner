package com.hal.stun.message.attribute;

import com.hal.stun.message.StunParseException;
import com.hal.stun.message.StunMessageUtils;
import java.util.List;
import java.util.ArrayList;

public class StunAttribute {
  
  private AttributeType attributeType; // the attribute type to be set by the implementing subclass
  private int length; // the size of this attribute's value in bytes
  private String valueHex; // hex encoded valueHex of the attribute
  public StunAttribute(int attributeType, int length, String valueHex) throws StunParseException {
    this.attributeType = AttributeType.fromBytes((short) attributeType);
    this.length = length;
    this.valueHex = valueHex;
    verifyValueLength(valueHex, length);
  }
  
  private static void verifyValueLength(String valueHex, int length) throws StunParseException {
    int hexStringLength = valueHex.length()/2; // each hex char encodes 4 bits
    if (hexStringLength != length) {
      throw new StunParseException("attribute valueHex " + valueHex + " is " + hexStringLength 
        + " bytes, but the attribute length specified is " + length);
    }
  }
  
  public byte[] toByteArray() {
    byte[] typeBytes = new byte[2];
    byte[] lengthBytes = new byte[2];
    typeBytes[0] = (byte) (attributeType >>> 8);
    typeBytes[1] = (byte) attributeType;
    
    lengthBytes[0] = (byte) (length >>> 8);
    lengthBytes[1] = (byte) length;
    
    byte[] attributeValueBytes = StunMessageUtils.convertHexToByteArray(valueHex);    
    
    List<byte[]> unjoinedAttributeBytes = new ArrayList<byte[]>();
    unjoinedAttributeBytes.add(typeBytes);
    unjoinedAttributeBytes.add(lengthBytes);
    
    return StunMessageUtils.joinByteArrays(unjoinedAttributeBytes);
  }
  
  // public StunAttribute(byte[] attributeBytes) {
  //   this.attributeType = StunMessageUtils.extractByteSequence(attributeBytes, 0, 2);
  //   this.length = StunMessageUtils.extractByteSequence(attributeBytes, 2, 2);
  //   this.valueHex = StunMessageUtils.extractByteSequence(attributeBytes, 4, length);
  // }
  
  public int getAttributeType() {
    return attributeType;
  }
  
  public int getLength() {
    return length;
  }

  public String getValueAsHex() {
    return valueHex;
  }
  
  public static List<StunAttribute> parseAttributes(byte[] attributesBytes) throws StunParseException {
    validateAttributesBytes(attributesBytes);
    List<StunAttribute> attributes = new ArrayList<StunAttribute>();
    
    int offset = 0;
    int paddedLength = 0;
    while (offset + paddedLength < attributesBytes.length) {
      int attributeType = StunMessageUtils.extractByteSequence(attributesBytes, offset, 2);
      int length = StunMessageUtils.extractByteSequence(attributesBytes, offset + 2, 2);
      paddedLength = length;
      
      // round up to nearest multiple of 4
      int modValue = paddedLength%4;
      if (modValue > 0) {
        paddedLength += (4 - modValue);
      }
      String valueHex = StunMessageUtils.extractByteSequenceAsHex(attributesBytes, offset + 4, paddedLength, true);
      attributes.add(new StunAttribute(attributeType, length, valueHex));
      offset += paddedLength;
    }
    
    return attributes;
  }
  
  public static StunAttribute buildAttributeForType(int attributeType, int length, String valueHex) {
    switch(attributeType) {
      case 
    }
  }
  
  private static void validateAttributesBytes(byte[] attributesBytes) throws StunParseException {
    if (attributesBytes.length % 4 != 0) {
      throw new StunParseException("attributes must have bit count that is multiple of 32");
    }
    if (attributesBytes.length < 8) {
      throw new StunParseException("there must be at least one attribute of 16 bytes or more.");
    }
  }
  
}
