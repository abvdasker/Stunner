package com.hal.stun.message.attribute;

import com.hal.stun.message.StunMessageUtils;

import javax.xml.bind.DatatypeConverter;

public class UnrecognizedAttributeTypeException extends StunAttributeParseException {

    private short attributeType;

    public UnrecognizedAttributeTypeException(short type) {
        super("Unrecognized attribute type: " + formatType(type));
        this.attributeType = type;
    }

    public short getAttributeType() {
        return attributeType;
    }

    private static String formatType(short type) {
        byte[] typeBytes = StunMessageUtils.toBytes(type);
        return DatatypeConverter.printHexBinary(typeBytes);
    }

}
