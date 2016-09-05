package com.hal.stun;

import java.util.Map;
import java.net.InetSocketAddress;
import com.hal.stun.socket.StunMessageSocket;
import com.hal.stun.socket.UDPStunMessageSocket;
import com.hal.stun.socket.TCPStunMessageSocket;
import com.hal.stun.socket.NetworkMessage;
import com.hal.stun.socket.StunHandler;
import com.hal.stun.cli.SmartArgumentParser;
import com.hal.stun.cli.Help;
import com.hal.stun.cli.Argument;

import com.hal.stun.cli.ArgumentParseException;
import java.io.IOException;

public class StunServer {

  private static Map<String, Argument> parsedArgs;

  public static void main(String[] args) throws Exception {
    try {
      parsedArgs = SmartArgumentParser.parse(args);

      if (parsedArgs.get("--help").getBoolean()) {
        printHelpAndDie(0);
      }

      boolean runUDP = parsedArgs.get("--udp").getBoolean();
      boolean runTCP = parsedArgs.get("--tcp").getBoolean();

      if (runUDP) {
        Thread tcpServerThread = createTCPServer();
        tcpServerThread.start();
      }
      if (runTCP) {
        Thread udpServerThread = createUDPServer();
        udpServerThread.start();
      }
    } catch (ArgumentParseException exception) {
      System.out.println(exception.getMessage() + "\n");
      printHelpAndDie(1);
    }
  }

  private static Thread createTCPServer() throws IOException, ArgumentParseException {
    final StunHandler handler = createStunHandler();
    int tcpPort = -1;
    tcpPort = parsedArgs.get("--tcpport").getInt();
    final StunMessageSocket tcpSocket = new TCPStunMessageSocket(tcpPort);
    return new Thread(createServer(tcpSocket, handler));
  }

  private static Thread createUDPServer() throws IOException, ArgumentParseException {
    final StunHandler handler = createStunHandler();
    int udpPort = parsedArgs.get("--udpport").getInt();
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

  private static void printHelpAndDie(int exitCode) {
    System.out.println(Help.getHelpText());
    System.exit(exitCode);
  }
}
