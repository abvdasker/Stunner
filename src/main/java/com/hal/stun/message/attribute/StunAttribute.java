package com.hal.stun.message.attribute;

import com.hal.stun.message.StunParseException;
import com.hal.stun.message.StunMessageUtils;
import com.hal.stun.message.attribute.value.StunAttributeValue;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class StunAttribute {

  private static final int ATTRIBUTE_HEADER_SIZE_BYTES = 4;
  
  private AttributeType attributeType; // the attribute type to be set by the implementing subclass
  private int length; // the size of this attribute's value in bytes
  private StunAttributeValue attributeValue;
  public StunAttribute(AttributeType attributeType, int length, byte[] value) throws StunParseException {
    this.attributeType = attributeType;
    this.length = length;
    verifyValueLength(value);
    byte[] actualValue = valueFromLength(value, length);
    this.attributeValue = attributeType.buildAttributeValue(actualValue);
  }

  private void verifyValueLength(byte[] value) throws StunParseException {
    if (!lengthIsValid(value)) {
      String valueHex = StunMessageUtils.convertByteArrayToHex(value);
      throw new StunParseException("attribute valueHex " + valueHex + " is " + value.length 
        + " bytes, but the attribute length specified is " + length);
    }
  }

  public byte[] toByteArray() {
    byte[] typeBytes = new byte[2];
    byte[] lengthBytes = new byte[2];
    
    short attributeTypeValue = attributeType.getTypeBytes();
    typeBytes[0] = (byte) (attributeTypeValue >>> 8);
    typeBytes[1] = (byte) attributeTypeValue;

    lengthBytes[0] = (byte) (length >>> 8);
    lengthBytes[1] = (byte) length;
    
    List<byte[]> unjoinedAttributeBytes = new ArrayList<byte[]>();
    unjoinedAttributeBytes.add(typeBytes);
    unjoinedAttributeBytes.add(lengthBytes);
    unjoinedAttributeBytes.add(getValueBytes());

    return StunMessageUtils.joinByteArrays(unjoinedAttributeBytes);
  }
  
  public AttributeType getAttributeType() {
    return attributeType;
  }
  
  public int getLength() {
    return length;
  }

  public byte[] getValueBytes() {
    return attributeValue.getBytes();
  }

  public StunAttributeValue getValue() {
    return attributeValue;
  }
  
  public static List<StunAttribute> parseAttributes(byte[] attributesBytes) throws StunParseException {
    validateAttributesBytes(attributesBytes);
    List<StunAttribute> attributes = new ArrayList<StunAttribute>();
    
    int offset = 0;
    int attributeCount = 1;
    int headersize = ATTRIBUTE_HEADER_SIZE_BYTES;
    int paddedLength = 0;
    while (offset + paddedLength < attributesBytes.length) {
      int attributeType = StunMessageUtils.extractByteSequence(attributesBytes, offset, 2);
      int length = StunMessageUtils.extractByteSequence(attributesBytes, offset + 2, 2);
      // the length is in the attribute header.
      // Anything extra is discarded

      int arrayStart = offset + ATTRIBUTE_HEADER_SIZE_BYTES;
      paddedLength = roundUpLength(length);
      int arrayEnd = arrayStart + paddedLength;
      byte[] value = Arrays.copyOfRange(attributesBytes, arrayStart, arrayEnd);

      AttributeType type;
      try {
        type = AttributeType.fromBytes((short) attributeType);
      } catch (UnrecognizedAttributeTypeException exception) {
        // TODO: implement error handling to add unrecognized attribute to response.
        throw new RuntimeException(exception);
      }
      attributes.add(new StunAttribute(type, length, value));

      offset += paddedLength + ATTRIBUTE_HEADER_SIZE_BYTES;
    }
    
    return attributes;
  }
  
  private static void validateAttributesBytes(byte[] attributesBytes) throws StunParseException {
    if (attributesBytes.length % 4 != 0) {
      throw new StunParseException("attributes must have bit count that is multiple of 32");
    }
    if (attributesBytes.length < 8) {
      throw new StunParseException("there must be at least one attribute of 16 bytes or more.");
    }
  }

  private static int roundUpLength(int length) {
    int paddedLength = length;
    // round up to nearest multiple of 4
    int modValue = paddedLength % 4;
    if (modValue > 0) {
      paddedLength += (4 - modValue);
    }
    return paddedLength;
  }

  private static byte[] valueFromLength(byte[] rawValue, int length) {
    return Arrays.copyOfRange(rawValue, 0, length);
  }

  private boolean lengthIsValid(byte[] value) {
    return (length <= value.length) && (length > value.length - 4);
  }
}
