package com.hal.stun;

import com.hal.stun.socket.StunMessageSocket;
import com.hal.stun.socket.UDPStunMessageSocket;
import com.hal.stun.socket.NetworkMessage;
import com.hal.stun.socket.StunHandler;
import com.hal.stun.log.Logger;

import java.net.InetSocketAddress;

import java.io.IOException;

public class StunServer {

  private static final Logger log = new Logger();

  public static void main(String[] args) throws IOException, Exception {

    final StunMessageSocket udpSocket = new UDPStunMessageSocket();
    final StunApplication application = new StunApplication();
    /**
      * The input stream from one request. This stream will
      * close when the connection is closed. Until then it will block
      * on read(). Use a input read to parse the request into lines.
      * when an empty line is received, that is the end of the request.
      */
    
    while (true) {
      udpSocket.handle(new StunHandler() {
        public NetworkMessage handle(NetworkMessage request) throws IOException {
          byte[] requestBytes = request.getData();
          InetSocketAddress clientSocketAddress = request.getSocketAddress();
          log.print(requestBytes.length + " bytes received");
          
          byte[] responseData;
          try {
            responseData = application.handle(requestBytes, clientSocketAddress);
          } catch (Exception exception) {
            throw new RuntimeException(exception);
          }
          return new NetworkMessage(clientSocketAddress, responseData);
        }
      });
    }

  }  
}
