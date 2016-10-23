package com.hal.stun.message;

import com.hal.stun.message.StunParseException;
import com.hal.stun.message.attribute.StunAttribute;
import com.hal.stun.message.attribute.AttributeType;
import java.util.List;
import java.util.ArrayList;

import java.net.InetSocketAddress;

// TODO: this should be abstract with request/response subclasses
public class StunMessage {

  protected byte[] messageBytes;
  protected StunHeader header;
  protected List<StunAttribute> attributes;
  protected InetSocketAddress address;

  public StunMessage(byte[] message, InetSocketAddress address) throws StunParseException {
    // TODO: log formatted raw message
    this.messageBytes = message;
    this.address = address;
    header = new StunHeader(getHeaderBytes(message));
    header.validateMessageLength(message);
    attributes = StunAttribute.parseAttributes(getAttributesBytes(message));
  }

  public StunHeader getHeader() {
    return header;
  }

  public byte[] getBytes() {
    List<byte[]> messageByteArray = new ArrayList<byte[]>();
    messageByteArray.add(header.getBytes());
    for (StunAttribute attribute : attributes) {
        messageByteArray.add(attribute.getBytes());
    }
    return StunMessageUtils.joinByteArrays(messageByteArray);
  }

  public byte[] getBytesNoFingerprint() {
    List<byte[]> messageByteArray = new ArrayList<byte[]>();
    messageByteArray.add(header.getBytes());
    for (StunAttribute attribute : attributes) {
      if (attribute.getAttributeType() != AttributeType.FINGERPRINT) {
        messageByteArray.add(attribute.getBytes());
      }
    }
    return StunMessageUtils.joinByteArrays(messageByteArray);
  }

  public List<StunAttribute> getAttributes() {
    return attributes;
  }

  public InetSocketAddress getAddress() {
    return address;
  }

  protected StunMessage() {
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
