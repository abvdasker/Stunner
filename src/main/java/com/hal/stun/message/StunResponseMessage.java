package com.hal.stun.message;

import com.hal.stun.message.attribute.AttributeType;
import com.hal.stun.message.attribute.StunAttribute;
import com.hal.stun.message.attribute.value.XORMappedAddressStunAttributeValue;
import com.hal.stun.message.attribute.value.SoftwareStunAttributeValue;
import com.hal.stun.message.attribute.value.FingerprintStunAttributeValue;

import java.util.ArrayList;
import java.util.List;

public class StunResponseMessage extends StunMessage {

  private static final String SOFTWARE_NAME = "test vector";
    //"STUNNER STUN server;" + System.getProperty("os.name") + ";Java " + System.getProperty("java.version");

  // TODO: fix data modeling. Avoid multiple constructors of superclass
  public StunResponseMessage(StunMessage requestMessage) throws StunParseException {
    super();
    attributes = buildResponseAttributes(requestMessage);
    int messageLength = getAttributeListByteLength(attributes);
    header = new StunHeader(MessageClass.SUCCESS,
                            StunHeader.BINDING_METHOD,
                            messageLength,
                            requestMessage.getHeader().getTransactionID());

    updateFingerprint(attributes);
  }

  public StunResponseMessage() {
    super();
  }

  private static List<StunAttribute> buildResponseAttributes(StunMessage request) throws StunParseException {
    StunHeader requestHeader = request.getHeader();
    List<StunAttribute> attributes = new ArrayList<StunAttribute>();
    attributes.add(buildSoftwareAttribute());
    if (requestHeader.getMessageMethod() == StunHeader.BINDING_METHOD) {
      attributes.add(buildXORMappedAddressAttribute(request));
    }
    attributes.add(buildFingerprintAttribute());

    return attributes;
  }

  protected static StunAttribute buildSoftwareAttribute() {
    try {
      SoftwareStunAttributeValue softwareValue = new SoftwareStunAttributeValue(SOFTWARE_NAME);
      return new StunAttribute(AttributeType.SOFTWARE, softwareValue);
    } catch (StunParseException exception) {
      throw new RuntimeException(exception);
    }
  }

  private static StunAttribute buildXORMappedAddressAttribute(StunMessage request) throws StunParseException {
    StunHeader requestHeader = request.getHeader();
    XORMappedAddressStunAttributeValue xORMappedAddressValue = new XORMappedAddressStunAttributeValue(request.getAddress(),
                                                                                                      requestHeader.getTransactionID());
    return new StunAttribute(AttributeType.XOR_MAPPED_ADDRESS, xORMappedAddressValue);
  }

  protected static StunAttribute buildFingerprintAttribute() {
    try {
      return new StunAttribute(AttributeType.FINGERPRINT,
                               FingerprintStunAttributeValue.VALUE_SIZE_BYTES,
                               new byte[FingerprintStunAttributeValue.VALUE_SIZE_BYTES]);
    } catch (StunParseException exception) {
      throw new RuntimeException(exception);
    }
  }

  // avoid this inefficiency by simply adding 4 to the attribute's length field
  protected static int getAttributeListByteLength(List<StunAttribute> responseAttributes) {
    int responseBodyByteLength = 0;
    for (StunAttribute attribute : responseAttributes) {
      responseBodyByteLength += attribute.getWholeLength();
    }
    
    return responseBodyByteLength;
  }

  protected void updateFingerprint(List<StunAttribute> responseAttributes) {
    for (StunAttribute attribute : responseAttributes) {
      if (attribute.getAttributeType() == AttributeType.FINGERPRINT) {
        FingerprintStunAttributeValue value = (FingerprintStunAttributeValue) attribute.getValue();
        value.update(getBytesNoFingerprint());
        break;
      }
    }
  }
}
