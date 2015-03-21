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
    
    /**
     * The input stream from one request. This stream will
     * close when the connection is closed. Until then it will block
     * on read(). Use a input read to parse the request into lines.
     * when an empty line is received, that is the end of the request.
     *
     */

    while (true) {
      InputStream in = listener.listen(); 
      String requestText = getRequestText(in);
      log.print(requestText);
    }
    
    
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