package com.hal.stun.message;

import com.hal.stun.message.StunParseException;
import com.hal.stun.message.attribute.StunAttribute;
import java.util.List;

import java.net.InetSocketAddress;

public class StunMessage {
  
  protected byte[] messageBytes;
  protected StunHeader header;
  protected List<StunAttribute> attributes;
  protected InetSocketAddress address;

  public StunMessage(byte[] message, InetSocketAddress address) throws StunParseException {
    this.messageBytes = message;
    this.address = address;
    header = new StunHeader(getHeaderBytes(message));
    header.validateMessageLength(message);
    attributes = StunAttribute.parseAttributes(getAttributesBytes(message));
  }
  
  public StunHeader getHeader() {
    return header;
  }
  
  // convert the header and attributes 
  // back into a byte array if it doesn't exist
  public byte[] getBytes() {
    byte[] headerBytes = header.getBytes();
    int messageByteLength = header.getMessageLength() + headerBytes.length;
    byte[] messageBytes = new byte[messageByteLength];
    int messageOffset = headerBytes.length;
    System.arraycopy(headerBytes, 0, messageBytes, 0, headerBytes.length);
    for (StunAttribute attribute : attributes) {
      byte[] attributeBytes = attribute.getBytes();
      System.arraycopy(attributeBytes, 0, messageBytes, messageOffset, attributeBytes.length);
      messageOffset += attributeBytes.length;
    }
    return messageBytes;
  }

  public List<StunAttribute> getAttributes() {
    return attributes;
  }

  public InetSocketAddress getAddress() {
    return address;
  }
  
  protected StunMessage() {
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
    for(int i = 0; i < attributesBytes.length; i++) {
      attributesBytes[i] = message[StunHeader.HEADER_SIZE + i]; 
    }
    
    return attributesBytes;
  }
  
}
