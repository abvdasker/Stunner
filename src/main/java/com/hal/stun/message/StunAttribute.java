package com.hal.stun.message;

import java.util.List;
import java.util.ArrayList;

// building the list of attributes is sort-of tricky...
public class StunAttribute {
  
  private int attributeType; // the attribute type to be set by the implementing subclass
  private int length; // the length of the encoded value in bytes
  private String value; // hex encoded value of the attribute value
  public StunAttribute(int attributeType, int length, String value) {
    this.attributeType = attributeType;
    this.length = length;
    this.value = value;
  }
  
  // public StunAttribute(byte[] attributeBytes) {
  //   this.attributeType = StunMessageUtils.extractByteSequence(attributeBytes, 0, 2);
  //   this.length = StunMessageUtils.extractByteSequence(attributeBytes, 2, 2);
  //   this.value = StunMessageUtils.extractByteSequence(attributeBytes, 4, length);
  // }
  
  public int getAttributeType() {
    return attributeType;
  }
  
  public int getLength() {
    return length;
  }

  public String getValueAsHex() {
    return value;
  }
  
  public static List<StunAttribute> parseAttributes(byte[] attributesBytes) throws StunParseException {
    validateAttributesBytes(attributesBytes);
    List<StunAttribute> attributes = new ArrayList<StunAttribute>();
    
    int offset = 0;
    while (offset < attributesBytes.length) {
      int attributeType = StunMessageUtils.extractByteSequence(attributesBytes, offset, 2);
      int length = StunMessageUtils.extractByteSequence(attributesBytes, offset + 2, 2);
      int paddedLength = length;
      while (paddedLength%4 != 0) { // round length up to nearest multiple of 4 bytes (32 bits)
        paddedLength += 1;
      }
      String value = StunMessageUtils.extractByteSequenceAsHex(attributesBytes, offset + 4, paddedLength);
      attributes.add(new StunAttribute(attributeType, length, value));
      offset += paddedLength;
    }
    
    return attributes;
  }
  
  private static void validateAttributesBytes(byte[] attributesBytes) throws StunParseException {
    if (attributesBytes.length % 4 != 0) {
      throw new StunParseException("attributes must have bit count that is multiple of 32");
    }
    if (attributesBytes.length < 8) {
      throw new StunParseException("an attribute must be at least 16 bytes.");
    }
  }
  
}