package com.hal.stun.message;

import com.hal.stun.message.attribute.AttributeType;
import com.hal.stun.message.attribute.StunAttribute;
import com.hal.stun.message.attribute.value.XORMappedAddressStunAttributeValue;

import java.util.ArrayList;
import java.util.List;

public class StunSuccessResponseMessage extends StunResponseMessage {

  //"STUNNER STUN server;" + System.getProperty("os.name") + ";Java " + System.getProperty("java.version");

  public StunSuccessResponseMessage(StunRequestMessage requestMessage) throws StunParseException {
    attributes = buildResponseAttributes(requestMessage);
    int messageLength = getAttributeListByteLength(attributes);
    header = new StunHeader(MessageClass.SUCCESS,
                            StunHeader.BINDING_METHOD,
                            messageLength,
                            requestMessage.getHeader().getTransactionID());
    updateFingerprint(attributes);
  }

  private static List<StunAttribute> buildResponseAttributes(StunRequestMessage request) throws StunParseException {
    StunHeader requestHeader = request.getHeader();
    List<StunAttribute> attributes = new ArrayList<StunAttribute>();
    attributes.add(buildSoftwareAttribute());
    if (requestHeader.getMessageMethod() == StunHeader.BINDING_METHOD) {
      attributes.add(buildXORMappedAddressAttribute(request));
    }
    attributes.add(buildFingerprintAttribute());

    return attributes;
  }

  private static StunAttribute buildXORMappedAddressAttribute(StunRequestMessage request) throws StunParseException {
    StunHeader requestHeader = request.getHeader();
    XORMappedAddressStunAttributeValue xORMappedAddressValue
      = new XORMappedAddressStunAttributeValue(request.getAddress(),
                                               requestHeader.getTransactionID());
    return new StunAttribute(AttributeType.XOR_MAPPED_ADDRESS, xORMappedAddressValue);
  }

}
