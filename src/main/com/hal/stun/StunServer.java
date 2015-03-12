package com.hal.stun;

import com.hal.stun.listener.Listener;
import com.hal.stun.log.Logger;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.io.IOException;

public class StunServer {
  
  private static final int PORT_NUMBER = 8000;
  private static final Logger log = new Logger();
  
  public static void main(String[] args) throws IOException {
    
    Listener listener = new Listener(PORT_NUMBER);
    
    /* 
     * The input stream from one request. This stream will
     * close when the connection is closed. Until then it will block
     * on read(). Use a input read to parse the request into lines.
     * when an empty line is received, that is the end of the request.
     *
     */
    InputStream in = listener.listen(); 
    String requestText = getRequestText(in);
    log.print(requestText);
    

  }
  
  private static String getRequestText(InputStream in) throws IOException {
    StringBuffer requestText = new StringBuffer();
    byte inputByte;
    char lastChar = ' ';
    char inputChar = ' ';
    
    while ((inputByte = (byte) in.read()) != -1) {
      inputChar = (char) inputByte;
      requestText.append(inputChar);
      log.print("_" + (int) lastChar + "_");
      log.print("_" + (int) inputChar + "_");
      if (inputChar == '\n') {
        log.print('\n');
      }
      // if (isLineEndChar(inputChar) && isLineEndChar(lastChar)) {
      //   break;
      // }
      lastChar = inputChar;
    }
    if (lastChar != ' ') {
      requestText.append(lastChar);
    }
    return requestText.toString();
  }
  
  private static InputStream getBufferedInputStreamUnlessBuffered(InputStream in) {
    if (in instanceof BufferedInputStream) {
      return in;
    } else {
      return new BufferedInputStream(in);
    }
  }
  
  private static boolean isLineEndChar(char character) {
    char newLine1 = (char) 10;
    char newLine2 = (char) 13;
    return character == newLine1 || character == newLine2;
  }
  
}