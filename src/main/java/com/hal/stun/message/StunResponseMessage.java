package com.hal.stun;

public class StunResponseMessage extends StunMessage {
  
  public StunResponseMessage(StunMessage requestMessage) {
    this.attributes = buildResponseAttributes(request);
    int messageLength = getAttributeListByteLength(responseAttributes);
    this.header = new StunHeader(MessageClass.SUCCESS, BINDING_METHOD, messageLength, request.transactionID);
  }
  
  private static List<StunAttribute> buildResponseAttributes(StunMessage request) {
    List<StunAttribute> attributes = new List<StunAttribute>();
    if (request.method == BINDING_METHOD) {
      // generate XOR mapping
    }
  }
  
  // given an input list of attributes, spits out the byte length. This is
  // potentially inefficient since it means converting the attributes to
  // byte arrays once here and later for serialization.
  private static int getAttributeListByteLength(responseAttributes) {
    int responseBodyByteLength = 0;
    for (StunAttribute attribute : responseAttributes) {
      responseByteLenth += attribute.toByteArray().length;
    }
    
    return responseByteLength;
  }
  
}