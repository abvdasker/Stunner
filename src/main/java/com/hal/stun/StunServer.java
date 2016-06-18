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

  private static Arguments parsedArgs;

  public static void main(String[] args) throws IOException, Exception {
    parsedArgs = new ArgumentParser(args).parse();

    boolean runUDP = parsedArgs.get("--udp");
    boolean runTCP = parsedArgs.get("--tcp");

    if (runUDP) {
      Thread tcpServerThread = createTCPServer();
      udpServerThread.start();
    }
    if (runTCP) {
      Thread udpServerThread = createUDPServer();
      tcpServerThread.start();
    }
  }

  private static Thread createTCPServer() throws IOException {
    final StunHandler handler = createStunHandler();
    int tcpPort = parsedArgs.getInt("--tcpport");
    final StunMessageSocket tcpSocket = new TCPStunMessageSocket(tcpPort);
    return new Thread(createServer(tcpSocket, handler));
  }

  private static Thread createUDPServer() throws IOException {
    final StunHandler handler = createStunHandler();
    int udpPort = parsedArgs.getInt("--udpport");
    final StunMessageSocket udpSocket = new UDPStunMessageSocket(udpPort);
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
