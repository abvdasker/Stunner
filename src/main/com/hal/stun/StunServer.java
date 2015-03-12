package com.hal.stun;

import com.hal.stun.listener.Listener;
import com.hal.stun.log.Logger;
import java.io.InputStream;

import java.io.IOException;

public class StunServer {
  
  private static final int PORT_NUMBER = 8000;
  private static final Logger log = new Logger();
  
  public static void main(String[] args) throws IOException {
    
    Listener listener = new Listener(PORT_NUMBER);
    
    /* 
     * The input stream from one request. This  will stream will
     * close when the connection is closed. Until then it will block
     * on read()
     *
     *
     */
    InputStream in = listener.listen(); 
    
    
    byte inputByte;
    char inputChar = ' ';
    StringBuffer buffer = new StringBuffer();
    String line;
    while ((inputByte = (byte) in.read()) != -1) {
      inputChar = (char) inputByte;
      buffer.append(inputChar);
      if (inputChar == '\n') {
        line = buffer.toString();
        buffer = new StringBuffer();
        log.print(line);
      }
      //log.print("\r " + buffer.length());
    }
    buffer.append(inputChar);
    if (inputChar == '\n') {
      line = buffer.toString();
      buffer = new StringBuffer();
      log.print(line);
    }
  }
  
}