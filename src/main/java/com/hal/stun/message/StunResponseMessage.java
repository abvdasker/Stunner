package com.hal.stun.message;

import com.hal.stun.message.attribute.AttributeType;
import com.hal.stun.message.attribute.StunAttribute;
import com.hal.stun.message.attribute.value.XORMappedAddressStunAttributeValue;
import com.hal.stun.message.attribute.value.SoftwareStunAttributeValue;

import java.util.ArrayList;
import java.util.List;

public class StunResponseMessage extends StunMessage {

  private static final String SOFTWARE_NAME = "STUN test client"//"STUNNER STUN server;" + System.getProperty("os.name") + ";Java " + System.getProperty("java.version");

  // TODO: fix data modeling. Avoid multiple constructors of superclass
  public StunResponseMessage(StunMessage requestMessage) throws StunParseException {
    super();
    this.attributes = buildResponseAttributes(requestMessage);
    int messageLength = getAttributeListByteLength(attributes);
    this.header = new StunHeader(
      MessageClass.SUCCESS,
      StunHeader.BINDING_METHOD,
      messageLength,
      requestMessage.getHeader().getTransactionID()
    );
  }

  private static List<StunAttribute> buildResponseAttributes(StunMessage request) throws StunParseException {
    StunHeader header = request.getHeader();
    List<StunAttribute> attributes = new ArrayList<StunAttribute>();
    if (header.getMessageMethod() == StunHeader.BINDING_METHOD) {

      SoftwareStunAttributeValue softwareValue = new SoftwareStunAttributeValue(SOFTWARE_NAME);
      byte[] softwareValueBytes = softwareValue.getBytes();
      StunAttribute softwareAttribute = new StunAttribute(AttributeType.SOFTWARE, softwareValueBytes.length, softwareValueBytes);
      attributes.add(softwareAttribute);

      // XOR address
      XORMappedAddressStunAttributeValue xORMappedAddress = new XORMappedAddressStunAttributeValue(request.getAddress(), header.getTransactionID());
      byte[] attributeValueBytes = xORMappedAddress.getBytes();
      StunAttribute xORAddressAttribute = new StunAttribute(AttributeType.XOR_MAPPED_ADDRESS, attributeValueBytes.length, attributeValueBytes);
      // TODO: pad attribute bytes up to nearest multiple of 4
      attributes.add(xORAddressAttribute);
    }
    return attributes;
  }

  // avoid this inefficiency by simply adding 4 to the attribute's length field
  private static int getAttributeListByteLength(List<StunAttribute> responseAttributes) {
    int responseBodyByteLength = 0;
    for (StunAttribute attribute : responseAttributes) {
      responseBodyByteLength += attribute.getWholeLength();
    }
    
    return responseBodyByteLength;
  }
}
