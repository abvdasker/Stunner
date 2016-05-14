package com.hal.stun;

import com.hal.stun.socket.StunMessageSocket;
import com.hal.stun.message.StunMessage;
import java.net.InetSocketAddress;

import com.hal.stun.client.StunTestClient;
import com.hal.stun.client.UDPStunTestClient;
import com.hal.stun.client.data.ClientTestData;
import com.hal.stun.message.StunMessageUtils;
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
      String expectedResponseHex = StunMessageUtils.formatByteArray(ClientTestData.BASIC_RESPONSE_IPV4);
      String actualResponseHex = StunMessageUtils.formatByteArray(response);
      System.out.println("expected response: \n" + expectedResponseHex + "\n\n actual response: \n" + actualResponseHex + " \n");
      /* TODO: Need to compare specific data and attributes rather than whole 
         response; alternately be able to run server in some sort of 
         "test mode" for test vector compatibility */
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
}
