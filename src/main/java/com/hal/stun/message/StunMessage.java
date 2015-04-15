package com.hal.stun.message;

import com.hal.stun.message.StunParseException;

public class StunMessage {

  public static final int HEADER_SIZE = 20;
  public static final short BINDING_METHOD = 0b000000000001;
  public static final int MAGIC_COOKIE = 0x2112A442;
  
  // NEVER FORGET to mask when upcasting from unsigned byte to int
  private static int MASK = 0xff;

  private byte[] messageBytes;
  private MessageClass messageClass;
  private short method; // 12 bits; binding method is 0b000000000001
  private int messageLength;

  // also add message length, magic cookie and transaction ID fields

  public StunMessage(byte[] message) throws StunParseException {
    this.messageBytes = message;
    parse(messageBytes);
  }

  private void parse(byte[] message) throws StunParseException {
    byte[] header = getHeaderBytes(message);
    parseHeader(header);
  }

  private static byte[] getHeaderBytes(byte[] message) throws StunParseException {
    if (message.length < HEADER_SIZE) {
      throw new StunParseException("message was smaller than header size. Header must be 20 bytes");
    }
    byte[] header = new byte[HEADER_SIZE];
    for (int i = 0; i < header.length; i++) {
      header[i] = message[i];
    }
    return header;
  }

  private void parseHeader(byte[] header) throws StunParseException {
    int firstByte = header[0] & MASK;
    if (firstByte>>>6 != 0) {
      throw new StunParseException("first two bits of header were not zero");
    }
    byte messageClassBits = getMessageClassBits(header);
    messageClass = MessageClass.fromByte(messageClassBits);
    method = getMessageMethod(header);
    if (method != BINDING_METHOD) {
      throw new StunParseException("unrecognized method. Only recognized method would be encoded with 0b000000000001");
    }
    messageLength = getMessageLength(header);
    verifyMagicCookie(header);
  }
  
  private static void verifyMagicCookie(byte[] header) throws StunParseException {
    int magicCookie = getMagicCookie(header);
    if (magicCookie != MAGIC_COOKIE) {
      throw new StunParseException("magic cookie must be " 
        + Integer.toHexString(MAGIC_COOKIE) + " but was " + Integer.toHexString(magicCookie) );
    }
  }

  private static byte getMessageClassBits(byte[] header) {
    byte messageClassBits = 0b0;
    int topBit = header[0] & MASK;
    int lowerBit = header[1] & MASK;
    topBit &= 0b00000001;
    lowerBit &= 0b00010000;
    topBit <<= 1;
    lowerBit >>>= 4;
    return (byte) (topBit | lowerBit);
  }

  private static short getMessageMethod(byte[] header) {
    int topBits = header[0] & MASK;
	
    // removes the included class bit from upper byte and shifts down
    topBits >>>= 1;
    topBits <<= 9;
	
    // removes the included class bit from lower byte and shifts down
    int lowerBits = header[1];
    int lowerTop3 = lowerBits & 0b111000000;
    lowerTop3 >>>= 1;
    int lowerBottom4 = lowerBits & 0b00001111;
    lowerBits = lowerTop3 | lowerBottom4; 

    short fullBits = 0;
    fullBits |= (short) topBits;
    // shift upper bits down to fill in removed class bit from lower bits
    fullBits >>>= 1;
        
    // combine with 7 lower method bits
    fullBits |= (short) lowerBits;
    return fullBits;
  }

  private static int getMessageLength(byte[] header) throws StunParseException {
    int messageLengthTop = header[2] & MASK;
    int messageLengthBottom = header[3] & MASK;
    int messageLength = 0;
    messageLength |= messageLengthTop;
    messageLength <<= 8;
    messageLength |= messageLengthBottom;
    if (messageLength%4 != 0) {
      throw new StunParseException("last 2 bits of message length must be 0");
    }
    
    return messageLength;
  }
  
  private static int getMagicCookie(byte[] header) throws StunParseException {
    int magicCookie = header[4] & MASK;
    magicCookie <<= 8;
    magicCookie |= (header[5] & MASK);
    magicCookie <<= 8;
    magicCookie |= (header[6] & MASK);
    magicCookie <<= 8;
    magicCookie |= (header[7] & MASK);
    return magicCookie;
  }
  
}