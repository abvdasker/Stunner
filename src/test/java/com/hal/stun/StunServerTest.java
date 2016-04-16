package com.hal.stun;

import com.hal.stun.socket.StunMessageSocket;
import com.hal.stun.message.StunMessage;
import java.net.InetSocketAddress;

import javax.xml.bind.DatatypeConverter;

import com.hal.stun.client.StunTestClient;
import com.hal.stun.client.UDPStunTestClient;
import com.hal.stun.client.data.ClientTestData;
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

import java.net.SocketTimeoutException;

public class StunServerTest {

  private static final String LOCAL_SERVER_ADDRESS_V4 = "127.0.0.1";

  @Test
  public void testUDPOverIPv4() throws Exception {
    Thread serverThread = startServer();

    try {
      InetSocketAddress serverAddress
        = new InetSocketAddress(LOCAL_SERVER_ADDRESS_V4, StunMessageSocket.DEFAULT_PORT);
      StunTestClient client = new UDPStunTestClient(serverAddress);
      byte[] response = client.send(ClientTestData.BASIC_REQUEST_IPV4);
      String expectedResponseHex = formatByteArray(ClientTestData.BASIC_RESPONSE_IPV4);
      String actualResponseHex = formatByteArray(response);
      System.out.println("expected response: \n" + expectedResponseHex + "\n\n actual response: \n" + actualResponseHex + " \n");
      Assert.assertEquals("response message matches expected response",
                          ClientTestData.BASIC_RESPONSE_IPV4,
                          response);
    } catch (SocketTimeoutException exception) {
      Assert.fail();
    } finally {
      serverThread.interrupt();
    }
  }

  private static Thread startServer() {
    Runnable server = new Runnable() {
        public void run() {
          try {
            StunServer.main(new String[0]);
          } catch (Exception exception) {
            throw new RuntimeException(exception);
          }
        }
      };
    Thread serverThread = new Thread(server);
    serverThread.start();
    return serverThread;
  }

  private static String formatByteArray(byte[] bytes) {
    String bytesHex = DatatypeConverter.printHexBinary(bytes);
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < bytesHex.length(); i++) {
      char c = bytesHex.charAt(i);
      if (i > 0) {
        if (i % 8 == 0) {
          buffer.append('\n');
        } else if (i % 2 == 0) {
          buffer.append(' ');
        }
      }
      buffer.append(c);
    }
    return buffer.toString();
  }
}
