package com.hal.stun;

import com.hal.stun.socket.StunMessageSocket;
import com.hal.stun.socket.UDPStunMessageSocket;
import com.hal.stun.socket.TCPStunMessageSocket;
import com.hal.stun.socket.NetworkMessage;
import com.hal.stun.socket.StunHandler;
import com.hal.stun.log.Logger;

import java.net.InetSocketAddress;

import java.io.IOException;

public class StunServer {

  private static final Logger log = new Logger();

  public static final int DEFAULT_TCP_PORT = 8000;
  public static final int DEFAULT_UDP_PORT = 8001;

  public static void main(String[] args) throws IOException, Exception {

    Thread tcpServerThread = createTCPServer();
    Thread udpServerThread = createUDPServer();
    tcpServerThread.start();
    udpServerThread.start();
  }

  private static Thread createTCPServer() throws IOException {
    final StunHandler handler = createStunHandler();
    final StunMessageSocket tcpSocket = new TCPStunMessageSocket(DEFAULT_TCP_PORT);
    return new Thread(createServer(tcpSocket, handler));
  }

  private static Thread createUDPServer() throws IOException {
    final StunHandler handler = createStunHandler();
    final StunMessageSocket udpSocket = new UDPStunMessageSocket(DEFAULT_UDP_PORT);
    return new Thread(createServer(udpSocket, handler));
  }

  private static Runnable createServer(final StunMessageSocket socket, final StunHandler handler) {
    return new Runnable() {
      public void run() {
        try {
          while (true) {
            socket.handle(handler);
          }
        } catch (Exception exception) {
          throw new RuntimeException(exception);
        }
      }
    };
  }

  private static final StunHandler createStunHandler() {
    final StunApplication application = new StunApplication();
    return new StunHandler() {
      public NetworkMessage handle(NetworkMessage request) throws IOException {
        byte[] requestBytes = request.getData();
        InetSocketAddress clientSocketAddress = request.getSocketAddress();

        byte[] responseData;
        try {
          responseData = application.handle(requestBytes, clientSocketAddress);
        } catch (Exception exception) {
          throw new RuntimeException(exception);
        }
        return new NetworkMessage(clientSocketAddress, responseData);
      }
    };
  }
}
