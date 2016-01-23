package com.hal.stun;

import com.hal.stun.socket.StunMessageSocket;
import com.hal.stun.socket.UDPStunMessageSocket;
import com.hal.stun.socket.NetworkMessage;
import com.hal.stun.log.Logger;
import com.hal.stun.message.StunMessage;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.ByteBuffer;
import java.net.InetSocketAddress;

import java.io.IOException;

public class StunServer {

  private static final int PORT_NUMBER = 8000;
  private static final int MAX_BUFFER_BYTES = 7000; // probably too large
  private static final Logger log = new Logger();

  public static void main(String[] args) throws IOException, Exception {

    StunMessageSocket udpSocket = new UDPStunMessageSocket();
    StunApplication application = new StunApplication();
    /**
      * The input stream from one request. This stream will
      * close when the connection is closed. Until then it will block
      * on read(). Use a input read to parse the request into lines.
      * when an empty line is received, that is the end of the request.
      */
    
    while (true) {
      NetworkMessage request = udpSocket.listen();
      byte[] requestBytes = request.getData();
      InetSocketAddress clientSocketAddress = request.getSocketAddress();
      log.print(requestBytes.length + " bytes received");

      byte[] responseData = application.handle(requestBytes, clientSocketAddress);
      NetworkMessage response = new NetworkMessage(clientSocketAddress, responseData);
      udpSocket.transmit(response);
    }

  }  
}
