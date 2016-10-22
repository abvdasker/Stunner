package com.hal.stun;

import java.util.Map;
import java.net.InetSocketAddress;
import com.hal.stun.socket.StunMessageSocket;
import com.hal.stun.socket.UDPStunMessageSocket;
import com.hal.stun.socket.TCPStunMessageSocket;
import com.hal.stun.socket.NetworkMessage;
import com.hal.stun.socket.StunHandler;
import com.hal.stun.cli.ArgumentParser;
import com.hal.stun.cli.Help;
import com.hal.stun.cli.Argument;

import com.hal.stun.cli.ArgumentParseException;
import java.io.IOException;
import java.util.logging.Logger;

public class StunServer {

  private static Map<String, Argument> parsedArgs;
  private static final Logger log = Logger.getLogger(StunServer.class.getName());

  public static void main(String[] args) {
    try {
      parsedArgs = ArgumentParser.parse(args);

      if (parsedArgs.get("--help").getBoolean()) {
        printHelpAndDie(0);
      }

      boolean runTCP = parsedArgs.get("--tcp").getBoolean();
      boolean runUDP = parsedArgs.get("--udp").getBoolean();

      if (runTCP) {
        log.info("starting TCP server");
        Thread tcpServerThread = createTCPServer();
        tcpServerThread.start();
      }
      if (runUDP) {
        log.info("starting UDP server");
        Thread udpServerThread = createUDPServer();
        udpServerThread.start();
      }
    } catch (ArgumentParseException exception) {
      log.severe("Exception parsing arguments:\n" + exception.getMessage());
      printHelpAndDie(1);
    } catch (Exception exception) {
      log.severe("Fatal error:\n" + exception.getMessage());
      System.exit(1);
    }
  }

  private static Thread createTCPServer() throws IOException, ArgumentParseException {
    final StunHandler handler = createStunHandler();
    int tcpPort = parsedArgs.get("--tcpport").getInt();
    logServerStart("TCP", tcpPort);
    final StunMessageSocket tcpSocket = new TCPStunMessageSocket(tcpPort);
    return new Thread(createServer(tcpSocket, handler));
  }

  private static Thread createUDPServer() throws IOException, ArgumentParseException {
    final StunHandler handler = createStunHandler();
    int udpPort = parsedArgs.get("--udpport").getInt();
    logServerStart("UDP", udpPort);
    final StunMessageSocket udpSocket = new UDPStunMessageSocket(udpPort);
    return new Thread(createServer(udpSocket, handler));
  }

  private static void logServerStart(String serverType, int port) {
    log.info(serverType + " server listening on port " + port);
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
    log.info(Help.getHelpText());
    System.exit(exitCode);
  }
}
