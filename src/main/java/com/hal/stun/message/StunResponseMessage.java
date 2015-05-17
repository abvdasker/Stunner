package com.hal.stun.message;

import java.util.ArrayList;
import java.util.List;

public class StunResponseMessage extends StunMessage {
  
  public StunResponseMessage(StunMessage requestMessage) {
    super();
    this.attributes = buildResponseAttributes(requestMessage);
    int messageLength = getAttributeListByteLength(attributes);
    this.header = new StunHeader(MessageClass.SUCCESS, StunHeader.BINDING_METHOD, messageLength, requestMessage.getHeader().getTransactionID());
  }
  
  private static List<StunAttribute> buildResponseAttributes(StunMessage request) {
    List<StunAttribute> attributes = new ArrayList<StunAttribute>();
    if (request.getHeader().getMessageMethod() == StunHeader.BINDING_METHOD) {
      // generate XOR mapping
    }
    return attributes;
  }
  
  // avoid this inefficiency by simply adding 4 to the attribute's length field
  private static int getAttributeListByteLength(List<StunAttribute> responseAttributes) {
    int responseBodyByteLength = 0;
    for (StunAttribute attribute : responseAttributes) {
      responseBodyByteLength += attribute.getLength() + 4;
    }
    
    return responseBodyByteLength;
  }
  
}