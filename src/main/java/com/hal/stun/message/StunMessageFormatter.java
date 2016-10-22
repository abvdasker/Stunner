package com.hal.stun.message;

import com.hal.stun.message.attribute.StunAttribute;
import com.hal.stun.message.attribute.AttributeType;
import com.hal.stun.message.attribute.value.StunAttributeValue;

import java.util.List;
import javax.xml.bind.DatatypeConverter;

public class StunMessageFormatter {

  public static String formatMessage(StunMessage message) {
    return "header:\n" + formatHeader(message.getHeader()) +
      "\n" +
      "attributes:\n" + formatAttributes(message.getAttributes()) +
      "\n";
  }
  
  private static String formatHeader(StunHeader header) {
    StringBuffer headerStringBuffer = new StringBuffer();
    headerStringBuffer.append(formatMessageClass(header.getMessageClass()));
    headerStringBuffer.append('\n');
    headerStringBuffer.append(formatMethod(header.getMessageMethod()));
    headerStringBuffer.append('\n');
    headerStringBuffer.append(formatMessageLength(header.getMessageLength()));
    headerStringBuffer.append('\n');
    headerStringBuffer.append(formatTransactionID(header.getTransactionID()));
    return headerStringBuffer.toString();
  }

  private static String formatAttributes(List<StunAttribute> attributes) {
    StringBuffer attributesStringBuffer = new StringBuffer();
    for (StunAttribute attribute : attributes) {
      attributesStringBuffer.append(formatAttribute(attribute));
      attributesStringBuffer.append('\n');
    }
    return attributesStringBuffer.toString();
  }

  private static String formatAttribute(StunAttribute attribute) {
    StringBuffer attributeStringBuffer = new StringBuffer();
    attributeStringBuffer.append(formatAttributeType(attribute.getAttributeType()));
    attributeStringBuffer.append('\n');
    attributeStringBuffer.append(formatAttributeLength(attribute.getLength()));
    attributeStringBuffer.append('\n');
    attributeStringBuffer.append(formatAttributeValue(attribute.getValue()));
    return attributeStringBuffer.toString();
  }

  private static String formatAttributeType(AttributeType attributeType) {
    return "attribute type: " + attributeType;
  }

  private static String formatAttributeLength(int length) {
    return "length (bytes): " + length;
  }

  private static String formatAttributeValue(StunAttributeValue value) {
    return "value:\n" + value;
  }

  private static String formatTransactionID(byte[] transactionID) {
    return "transaction ID: " + DatatypeConverter.printBase64Binary(transactionID);
  }

  private static String formatMessageLength(int messageLength) {
    return "length (bytes): " + messageLength;
  }

  private static String formatMessageClass(MessageClass messageClass) {
    return "message class: " + messageClass;
  }

  private static String formatMethod(short method) {
    if (method == StunHeader.BINDING_METHOD) {
      return "method: BINDING";
    } else {
      return "method: " + method + " (unrecognized)";
    }
  }
}
