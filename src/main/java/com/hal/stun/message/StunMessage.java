package com.hal.stun.message;

import com.hal.stun.message.attribute.StunAttribute;

import java.util.List;
import java.net.InetSocketAddress;

public abstract class StunMessage {

  protected StunHeader header;
  protected List<StunAttribute> attributes;
  protected InetSocketAddress address;

  public StunHeader getHeader() {
    return header;
  }

  abstract public byte[] getBytes();

  public List<StunAttribute> getAttributes() {
    return attributes;
  }

  public InetSocketAddress getAddress() {
    return address;
  }

  protected static byte[] getHeaderBytes(byte[] message) throws StunParseException {
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
    for(int i = 0; i < attributesBytes.length; i++) {
      attributesBytes[i] = message[StunHeader.HEADER_SIZE + i]; 
    }

    return attributesBytes;
  }

  public String toString() {
    return StunMessageFormatter.formatMessage(this);
  }

}
