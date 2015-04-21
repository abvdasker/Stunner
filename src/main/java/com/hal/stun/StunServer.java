package com.hal.stun;

import com.hal.stun.listener.Listener;
import com.hal.stun.log.Logger;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.ByteBuffer;

import java.io.IOException;

public class StunServer {
  
  private static final int PORT_NUMBER = 8000;
  private static final int MAX_BUFFER_BYTES = 7000; // probably too large
  private static final Logger log = new Logger();
  
  public static void main(String[] args) throws IOException {
    
    Listener listener = new Listener(PORT_NUMBER);
    
    /**
      * The input stream from one request. This stream will
      * close when the connection is closed. Until then it will block
      * on read(). Use a input read to parse the request into lines.
      * when an empty line is received, that is the end of the request.
      */

    while (true) {
      InputStream in = listener.listen(); 
      byte[] requestBytes = getRequestBytes(in);
      log.print(requestBytes.length + " bytes received");
      //StunMessage message = new StunMessage(requestBytes);
      //String requestText = getRequestText(in);
      //log.print(requestText);
    }
    
    
  }
  
  private static byte[] getRequestBytes(InputStream in) throws IOException {
    if (! (in instanceof BufferedInputStream)) {
      in = new BufferedInputStream(in);
    }
    ByteBuffer byteBuffer = ByteBuffer.allocate(MAX_BUFFER_BYTES);
    byte inputByte;
    while((inputByte = (byte) in.read()) != -1) {
      byteBuffer.put(inputByte);
    }
    
    in.close();
    byteBuffer.compact();
    return byteBuffer.array();
  }
  
  private static String getRequestText(InputStream in) throws IOException {

    BufferedReader requestReader = new BufferedReader(new InputStreamReader(in));
    StringBuffer requestText = new StringBuffer();

    String line;
    while ( !(line = requestReader.readLine()).isEmpty()) {
      requestText.append(line + '\n');
    }
    in.close();

    return requestText.toString();
  }
  
  private static StunMessage buildResponse(StunMessage request) {
    StunMessage response = new StunMessage();
    
  }
  
}