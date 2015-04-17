package com.hal.stun.message;

import com.hal.stun.message.StunParseException;
import java.util.List;

public class StunMessage {
  
  private byte[] messageBytes;
  private StunHeader header;
  private List<StunAttribute> attributes;

  // also add message length, magic cookie and transaction ID fields

  public StunMessage(byte[] message) throws StunParseException {
    this.messageBytes = message;
    header = new StunHeader(getHeaderBytes(message));
    header.validateMessageLength(message);
    attributes = StunAttribute.parseAttributes(getAttributesBytes(message));
  }

  
  private static byte[] getHeaderBytes(byte[] message) throws StunParseException {
    if (message.length < StunHeader.HEADER_SIZE) {
      throw new StunParseException("message was smaller than header size. Header must be 20 bytes");
    }
    byte[] header = new byte[StunHeader.HEADER_SIZE];
    for (int i = 0; i < header.length; i++) {
      header[i] = message[i];
    }
    return header;
  }
  
  private static byte[] getAttributesBytes(byte[] message) throws StunParseException {
    byte[] attributesBytes = new byte[message.length - StunHeader.HEADER_SIZE];
    for(int i = message[StunHeader.HEADER_SIZE]; i < message.length; i++) {
      attributesBytes[i - StunHeader.HEADER_SIZE] = message[i]; 
    }
    
    return attributesBytes;
  }
  
}