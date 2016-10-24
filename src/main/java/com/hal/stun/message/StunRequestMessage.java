package com.hal.stun.message;

import com.hal.stun.message.StunParseException;
import com.hal.stun.message.attribute.StunAttribute;

import java.net.InetSocketAddress;

public class StunRequestMessage extends StunMessage {

  private byte[] messageBytes;
  public StunRequestMessage(byte[] message, InetSocketAddress address) throws StunParseException {
    messageBytes = message;
    this.address = address;
    header = new StunHeader(getHeaderBytes(message));
    header.validateMessageLength(message);
    attributes = StunAttribute.parseAttributes(getAttributesBytes(message));
  }

  private static byte[] getAttributesBytes(byte[] message) throws StunParseException {
    byte[] attributesBytes = new byte[message.length - StunHeader.HEADER_SIZE];
    for(int i = 0; i < attributesBytes.length; i++) {
      attributesBytes[i] = message[StunHeader.HEADER_SIZE + i]; 
    }

    return attributesBytes;
  }

  public byte[] getBytes() {
    return messageBytes;
  }

}
