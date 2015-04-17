package com.hal.stun.message;

public class StunMessageUtils {

  public static final int MASK = 0xff;
  
  public static int extractByteSequence(byte[] bytes, int startByteIndex, int bytesToExtract) {
    if (bytesToExtract > 4) {
      throw new RuntimeException("cannot cram more than 4 bytes into an int");
    }
    int endIndex = startByteIndex + bytesToExtract;
    
    int bytesAsInt = 0;
    for (int i = 0; i < bytesToExtract; i++) {
      bytesAsInt <<= 8;
      bytesAsInt |= (bytes[startByteIndex + i] & MASK);
    }
    
    return bytesAsInt;
  }
  
  public static String extractByteSequenceAsHex(byte[] bytes, int startByteIndex, int bytesToExtract) {
    StringBuffer outputBuffer = new StringBuffer();
    int endIndex = startByteIndex + bytesToExtract;

    int chunk = 0;
    for (int i = 0; i < bytesToExtract; i++) {
      chunk <<= 8;
      chunk |= (bytes[startByteIndex + i] & MASK);
      if ((i+1)%4 == 0) {
        outputBuffer.append(Integer.toHexString(chunk));
        chunk = 0;
      }
    }
    if (chunk != 0) {
      outputBuffer.append(Integer.toHexString(chunk));
    }
    
    return outputBuffer.toString();
  }
  
}