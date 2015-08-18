package com.hal.stun.message.attribute;

import com.hal.stun.message.StunParseException;
import com.hal.stun.message.StunMessageUtils;
import com.hal.stun.message.attribute.value.StunAttributeValue;
import com.hal.stun.message.attribute.value.MappedAddressStunAttributeValue;
import com.hal.stun.message.attribute.value.XORMappedAddressStunAttributeValue;
import com.hal.stun.message.attribute.UnrecognizedAttributeTypeException;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class StunAttribute {
  
  private AttributeType attributeType; // the attribute type to be set by the implementing subclass
  private int length; // the size of this attribute's value in bytes
  private StunAttributeValue attributeValue;
  protected StunAttribute(AttributeType attributeType, int length, String valueHex) throws StunParseException {
    this.attributeType = attributeType;
    this.length = length;
    this.attributeValue = buildAttributeValue(attributeType, valueHex);
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
    
    short attributeTypeValue = attributeType.getTypeBytes();
    typeBytes[0] = (byte) (attributeTypeValue >>> 8);
    typeBytes[1] = (byte) attributeTypeValue;
    
    lengthBytes[0] = (byte) (length >>> 8);
    lengthBytes[1] = (byte) length;
    
    String attributeValueHex = getValueAsHex();
    byte[] attributeValueBytes = StunMessageUtils.convertHexToByteArray(attributeValueHex);    
    
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
  
  public short getAttributeType() {
    return attributeType.getTypeBytes();
  }
  
  public int getLength() {
    return length;
  }

  public String getValueAsHex() {
    return attributeValue.getHexValue();
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
      
      AttributeType type;
      try {
        type = AttributeType.fromBytes((short) attributeType);
      } catch (UnrecognizedAttributeTypeException exception) {
        // TODO: implement error handling to add unrecognized attribute to response.
        System.out.println("TEMPORARY LACK OF ERROR HANDLING FOR UNRECOGNIZED attribute type triggered");
        throw new RuntimeException(exception);
      }
      attributes.add(new StunAttribute(type, length, valueHex));
      offset += paddedLength;
    }
    
    return attributes;
  }
  
  private static StunAttributeValue buildAttributeValue(AttributeType type, String valueHex)
      throws StunParseException {
    try {
      Class<? extends StunAttributeValue> attributeValueClass = type.getAttributeValueClass();
      Constructor<? extends StunAttributeValue> constructor = attributeValueClass.getConstructor(String.class);
      return constructor.newInstance(valueHex);
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException exception) {
      throw new RuntimeException("could not instantiate class", exception);
    } catch (InvocationTargetException exception) {
      Throwable cause = exception.getCause();
      if (cause instanceof StunParseException) {
        throw (StunParseException) cause;
      } else {
        throw new RuntimeException(cause);
      }
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
