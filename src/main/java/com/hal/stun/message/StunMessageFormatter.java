package com.hal.stun.message;

import com.hal.stun.message.attribute.StunAttribute;
import com.hal.stun.message.attribute.AttributeType;
import com.hal.stun.message.attribute.value.StunAttributeValue;

import java.util.List;
import javax.xml.bind.DatatypeConverter;

public class StunMessageFormatter {

    public static String formatMessage(StunMessage message) {
        StringBuffer messageStringBuffer = new StringBuffer();
        String spacing = '\n' + spacing(1);
        messageStringBuffer.append(spacing);
        messageStringBuffer.append("header:");
        messageStringBuffer.append(formatHeader(message.getHeader()));
        messageStringBuffer.append(spacing);
        messageStringBuffer.append("attributes:");
        messageStringBuffer.append(formatAttributes(message.getAttributes()));
        messageStringBuffer.append('\n');
        return messageStringBuffer.toString();
    }

    private static String formatHeader(StunHeader header) {
        StringBuffer headerStringBuffer = new StringBuffer();
        String spacing = '\n' + spacing(2);
        headerStringBuffer.append(spacing);
        headerStringBuffer.append(formatMessageClass(header.getMessageClass()));
        headerStringBuffer.append(spacing);
        headerStringBuffer.append(formatMethod(header.getMessageMethod()));
        headerStringBuffer.append(spacing);
        headerStringBuffer.append(formatMessageLength(header.getMessageLength()));
        headerStringBuffer.append(spacing);
        headerStringBuffer.append(formatTransactionID(header.getTransactionID()));
        return headerStringBuffer.toString();
    }

    private static String formatAttributes(List<StunAttribute> attributes) {
        StringBuffer attributesStringBuffer = new StringBuffer();
        for (StunAttribute attribute : attributes) {
            attributesStringBuffer.append('\n' + spacing(2));
            attributesStringBuffer.append("attribute:");
            attributesStringBuffer.append(formatAttribute(attribute));
        }
        return attributesStringBuffer.toString();
    }

    private static String formatAttribute(StunAttribute attribute) {
        StringBuffer attributeStringBuffer = new StringBuffer();
        String spacing = '\n' + spacing(3);
        attributeStringBuffer.append(spacing);
        attributeStringBuffer.append(formatAttributeType(attribute.getAttributeType()));
        attributeStringBuffer.append(spacing);
        attributeStringBuffer.append(formatAttributeLength(attribute.getLength()));
        attributeStringBuffer.append(spacing);
        attributeStringBuffer.append(formatAttributeValue(attribute.getValue()));
        attributeStringBuffer.append('\n');
        return attributeStringBuffer.toString();
    }

    private static String formatAttributeType(AttributeType attributeType) {
        return "attribute type: " + attributeType;
    }

    private static String formatAttributeLength(int length) {
        return "length (bytes): " + length;
    }

    private static String formatAttributeValue(StunAttributeValue value) {
        String valueString = value.toString();
        String spacing = '\n' + spacing(4);
        String spacedValueString = valueString.replace("\n", spacing);
        return "value:\n" + spacing + spacedValueString;
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

    private static String spacing(int numSpaces) {
        StringBuffer spaces = new StringBuffer();
        for (int i = 0; i < numSpaces; i++) {
            spaces.append("  ");
        }
        return spaces.toString();
    }
}
