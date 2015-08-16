package com.hal.stun.message;

import java.util.List;

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
  
  public static String extractByteSequenceAsHex(byte[] bytes, int startByteIndex, int bytesToExtract, boolean preserveLeadingZeroes) {
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
    
    StringBuffer leadingZeroes = new StringBuffer();
    if (preserveLeadingZeroes) {
      int outputBufferLength = outputBuffer.length();
      while (( (outputBufferLength + leadingZeroes.length())/2) < bytesToExtract) {
        leadingZeroes.append('0');
      }
    }
    return leadingZeroes.toString() + outputBuffer.toString();
  }
  
  public static byte[] joinByteArrays(List<byte[]> byteArrays) {
    int totalBytes = 0;
    for (byte[] byteArray : byteArrays) {
      totalBytes += byteArray.length;
    }
    
    byte[] joinedByteArrays = new byte[totalBytes];
    int i = 0;
    for (byte[] byteArray : byteArrays) {
      for (byte singleByte : byteArray) {
        joinedByteArrays[i] = singleByte;
        i++;
      }
    }
    
    return joinedByteArrays;
  }
  
  public static byte[] convertHexToByteArray(String hex) {
    int bufferLength = (int) Math.ceil(hex.length()/2.0);
    byte[] hexBytes = new byte[bufferLength];
    StringBuffer temp = new StringBuffer(2);
    int hexBytesIndex = 0;
    for (int i = 0; i < hex.length(); i++) {
      if (temp.length() == 2) {
        String byteHex = temp.toString();
        hexBytes[hexBytesIndex] = convertHexToByte(byteHex);
        temp = new StringBuffer(2);
        hexBytesIndex++;
      }
      temp.append(hex.charAt(i));
      // eat 2 characters into a string buffer
    }
    if (temp.length() > 0) {
      hexBytes[hexBytesIndex] = convertHexToByte(temp.toString());
    }
    
    return hexBytes;
  }
  
  private static byte convertHexToByte(String chars) {
    if (chars.length() != 2) {
      throw new RuntimeException("can only convery exactly 2-chars at a time");
    }
    byte val = (byte) (Integer.parseInt(chars, 16) & MASK);
    return val;
  }
  
}