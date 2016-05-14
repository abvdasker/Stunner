package com.hal.stun;

import com.hal.stun.socket.StunMessageSocket;
import com.hal.stun.message.StunMessage;
import java.net.InetSocketAddress;

import com.hal.stun.client.StunTestClient;
import com.hal.stun.client.UDPStunTestClient;
import com.hal.stun.client.data.ClientTestData;
import com.hal.stun.message.StunMessageUtils;
import com.hal.stun.message.attribute.value.XORMappedAddressStunAttributeValue;
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

import java.net.SocketTimeoutException;

public class StunServerTest {

  private static final String LOCAL_SERVER_ADDRESS_V4 = "127.0.0.1";

  @Before
  public void beforeEach() {
    // hack to avoid binding socket to unavailable port
    InetSocketAddress fakeRequestAddress = new InetSocketAddress("192.0.2.1", 32853);
    XORMappedAddressStunAttributeValue.setOverrideAddress(fakeRequestAddress);
  }

  @Test
  public void testUDPOverIPv4() throws Exception {
    Thread serverThread = startServer();

    try {
      InetSocketAddress serverAddress
        = new InetSocketAddress(LOCAL_SERVER_ADDRESS_V4, StunMessageSocket.DEFAULT_PORT);
      StunTestClient client = new UDPStunTestClient(serverAddress);
      byte[] response = client.send(ClientTestData.BASIC_REQUEST_IPV4);
      Assert.assertArrayEquals("response message matches expected response",
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
