package com.hal.stun.message;

import java.util.List;
import java.util.ArrayList;

import com.hal.stun.message.attribute.StunAttribute;
import com.hal.stun.message.attribute.AttributeType;
import com.hal.stun.message.attribute.value.SoftwareStunAttributeValue;
import com.hal.stun.message.attribute.value.FingerprintStunAttributeValue;
import com.hal.stun.message.attribute.value.BadRequestErrorCodeStunAttributeValue;

public class StunParseErrorResponseMessage extends StunResponseMessage {
  public StunParseErrorResponseMessage(byte[] rawRequest, StunParseException exception) {
    super();
    attributes = buildResponseAttributes(exception);
    int messageLength = getAttributeListByteLength(attributes);
    byte[] transactionID;
    try {
      StunHeader requestHeader = new StunHeader(getHeaderBytes(rawRequest));
      transactionID = requestHeader.getTransactionID();
    } catch (StunParseException headerParseException) {
      transactionID = new byte[0];
    }
    header = new StunHeader(MessageClass.ERROR,
                            StunHeader.BINDING_METHOD,
                            messageLength,
                            transactionID);
    updateFingerprint(attributes);
  }

  private static List<StunAttribute> buildResponseAttributes(StunParseException exception) {
    List<StunAttribute> attributes = new ArrayList<StunAttribute>();
    attributes.add(buildSoftwareAttribute());
    attributes.add(buildErrorCodeAttribute(exception));
    attributes.add(buildFingerprintAttribute());
    return attributes;
  }

  private static StunAttribute buildErrorCodeAttribute(StunParseException exception) {
    BadRequestErrorCodeStunAttributeValue errorCodeValue;
    try {
      errorCodeValue = new BadRequestErrorCodeStunAttributeValue(exception.getMessage());
    } catch (StunParseException attributeValueParseException) {
      throw new RuntimeException("Error creating error code attribute value");
    }
    return new StunAttribute(AttributeType.ERROR_CODE,
                             errorCodeValue);
  }
}
