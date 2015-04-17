package com.hal.stun.message;

import static com.hal.stun.message.StunMessageUtils.MASK;

public class StunHeader {
  
  // NEVER FORGET to mask when upcasting from unsigned byte to int
  public static final int HEADER_SIZE = 20;
  public static final short BINDING_METHOD = 0b000000000001;
  public static final int MAGIC_COOKIE = 0x2112A442;
  
  private byte[] headerBytes;
  private MessageClass messageClass;
  private short method; // 12 bits; binding method is 0b000000000001
  private int messageLength; // 16 bits; must be a multiple ov 4 (i.e. bottom 2 bits are 0)
  private String transactionID; // 96 bits;
  
  public StunHeader(byte[] headerBytes) throws StunParseException {
    this.headerBytes = headerBytes;
    parse(headerBytes);
  }
  
  private void parse(byte[] headerBytes) throws StunParseException {
    verifyFirstByte(headerBytes);
    byte messageClassBits = getMessageClassBits(headerBytes);
    messageClass = MessageClass.fromByte(messageClassBits);
    method = getMessageMethod(headerBytes);
    verifyMethod(method);
    messageLength = getMessageLength(headerBytes);
    verifyMagicCookie(headerBytes);
    transactionID = getTransactionID(headerBytes);
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
    int lowerBits = header[1] & MASK;
    int lowerTop3 = lowerBits & 0b11100000;
    lowerTop3 >>>= 1;
    int lowerBottom4 = lowerBits & 0b00001111;
    lowerBits = lowerTop3 | lowerBottom4; 

    short fullBits = 0;
    fullBits |= (short) topBits;
    // shift upper bits down to fill in removed class bit from lower bits
    fullBits >>>= 2;
        
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
    if ((messageLength%4) != 0) {
      throw new StunParseException("last 2 bits of message length must be 0");
    }
    
    return messageLength;
  }
  
  private static int getMagicCookie(byte[] header) {
    return StunMessageUtils.extractByteSequence(header, 4, 4);
  }
  
  private static String getTransactionID(byte[] header) {
    // transaction ID can be represented by 3 ints
    // convert ints to hex string
    int highestBytes = StunMessageUtils.extractByteSequence(header, 8, 4);
    int middleBytes = StunMessageUtils.extractByteSequence(header, 12, 4);
    int lowerBytes = StunMessageUtils.extractByteSequence(header, 16, 4);
    String transactionID = Integer.toHexString(highestBytes)
      + Integer.toHexString(middleBytes)
      + Integer.toHexString(lowerBytes);
    
    return transactionID;
  }
  
  private static void verifyFirstByte(byte[] headerBytes) throws StunParseException {
    int firstByte = headerBytes[0] & MASK;
    if (firstByte>>>6 != 0) {
      throw new StunParseException("first two bits of header were not zero");
    }
  }
  
  private static void verifyMethod(short method) throws StunParseException {
    if (method != BINDING_METHOD) {
      throw new StunParseException("unrecognized method. Only recognized method would be encoded with 0b000000000001");
    }
  }
  
  public void validateMessageLength(byte[] wholeMessage) 
  throws StunParseException {
    int actualMessageLength = wholeMessage.length - headerBytes.length;
    if (actualMessageLength != messageLength) {
      throw new StunParseException("message length mismatch! message body was " 
        + actualMessageLength + " bytes, but the header message length field indicated " 
        + actualMessageLength + " bytes.");
    }
  }
  
}