package com.hal.stun;

import com.hal.stun.socket.StunMessageSocket;
import java.net.InetSocketAddress;

import com.hal.stun.client.StunTestClient;
import com.hal.stun.client.UDPStunTestClient;
import com.hal.stun.client.data.ClientTestData;
import com.hal.stun.message.attribute.value.XORMappedAddressStunAttributeValue;
import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import java.net.SocketTimeoutException;
import java.net.SocketException;

public class StunServerTest {

  private static final String LOCAL_SERVER_ADDRESS_V4 = "127.0.0.1";
  private static final int TEST_PORT = 32853;

  private static StunTestClient client;
  private static Thread serverThread;

  @BeforeClass
  public static void beforeAll() throws SocketException {
    InetSocketAddress serverAddress
      = new InetSocketAddress(LOCAL_SERVER_ADDRESS_V4, StunMessageSocket.DEFAULT_PORT);
    client = new UDPStunTestClient(serverAddress);

    serverThread = startServer();
  }

  @AfterClass
  public static void afterAll() {
    serverThread.interrupt();
  }

  @Test
  public void testIPv4OverUDP() throws Exception {
    // hack to avoid binding socket to unavailable port
    setOverrideAddress("192.0.2.1");

    try {
      byte[] response = client.send(ClientTestData.BASIC_REQUEST_IPV4);
      Assert.assertArrayEquals("response message matches expected response",
                               ClientTestData.BASIC_RESPONSE_IPV4,
                               response);
    } catch (SocketTimeoutException exception) {
      Assert.fail();
    }
  }

  @Test
  public void testIPv6OverUDP() throws Exception {
    // hack to avoid binding socket to unavailable port
    setOverrideAddress("2001:db8:1234:5678:11:2233:4455:6677");

    try {
      byte[] response = client.send(ClientTestData.BASIC_REQUEST_IPV4);
      Assert.assertArrayEquals("response message matches expected response",
                               ClientTestData.BASIC_RESPONSE_IPV6,
                               response);
    } catch (SocketTimeoutException exception) {
      Assert.fail();
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

  private static void setOverrideAddress(String ip) {
    InetSocketAddress fakeRequestAddress = new InetSocketAddress(ip, TEST_PORT);
    XORMappedAddressStunAttributeValue.setOverrideAddress(fakeRequestAddress);
  }
}
