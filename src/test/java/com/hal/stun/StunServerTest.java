package com.hal.stun;

import com.hal.stun.socket.StunMessageSocket;
import com.hal.stun.cli.ArgumentParser;
import com.hal.stun.client.StunTestClient;
import com.hal.stun.client.UDPStunTestClient;
import com.hal.stun.client.TCPStunTestClient;
import com.hal.stun.client.data.ClientTestData;
import com.hal.stun.message.attribute.value.XORMappedAddressStunAttributeValue;
import java.net.InetSocketAddress;

import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import java.net.SocketTimeoutException;
import java.net.SocketException;

public class StunServerTest {

  private static final String LOCAL_SERVER_ADDRESS_V4 = "127.0.0.1";
  private static final int TEST_PORT = 32853;

  private static Thread serverThread;

  @BeforeClass
  public static void beforeAll() throws SocketException {
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
    InetSocketAddress serverAddress
      = new InetSocketAddress(LOCAL_SERVER_ADDRESS_V4, ArgumentParser.DEFAULT_UDP_PORT);

    StunTestClient client = new UDPStunTestClient(serverAddress);
    try {
      byte[] response = client.send(ClientTestData.BASIC_REQUEST_IPV4);
      Assert.assertArrayEquals("response message matches expected response",
                               ClientTestData.BASIC_RESPONSE_IPV4,
                               response);
    } finally {
      client.close();
    }
  }

  @Test
  public void testIPv6OverUDP() throws Exception {
    // hack to avoid binding socket to unavailable port
    setOverrideAddress("2001:db8:1234:5678:11:2233:4455:6677");
    InetSocketAddress serverAddress
      = new InetSocketAddress(LOCAL_SERVER_ADDRESS_V4, ArgumentParser.DEFAULT_UDP_PORT);

    StunTestClient client = new UDPStunTestClient(serverAddress);
    try {
      byte[] response = client.send(ClientTestData.BASIC_REQUEST_IPV4);
      Assert.assertArrayEquals("response message matches expected response",
                               ClientTestData.BASIC_RESPONSE_IPV6,
                               response);
    } finally {
      client.close();
    }
  }
  
  @Test
  public void testIPv4OverTCP() throws Exception {
    setOverrideAddress("192.0.2.1");
    InetSocketAddress serverAddress
      = new InetSocketAddress(LOCAL_SERVER_ADDRESS_V4, ArgumentParser.DEFAULT_TCP_PORT);

    StunTestClient client = new TCPStunTestClient(serverAddress);
    try {
      byte[] response = client.send(ClientTestData.BASIC_REQUEST_IPV4);
      Assert.assertArrayEquals("response message matches expected response",
                               ClientTestData.BASIC_RESPONSE_IPV4,
                               response);
    } finally {
      client.close();
    }
  }

  private static Thread startServer() {
    final Object mutex = new Object();
    Runnable server = new Runnable() {
        public void run() {
          try {
            StunServer.main(new String[0]);
            synchronized (mutex) {
              mutex.notifyAll();
            }
          } catch (Exception exception) {
            throw new RuntimeException(exception);
          }
        }
      };
    Thread serverThread = new Thread(server);
    serverThread.start();
    try {
      synchronized (mutex) {
        mutex.wait(1000);
      }
    } catch (InterruptedException exception) {
      System.out.println("interrupted while waiting on mutex!");
    }
    return serverThread;
  }

  private static void setOverrideAddress(String ip) {
    InetSocketAddress fakeRequestAddress = new InetSocketAddress(ip, TEST_PORT);
    XORMappedAddressStunAttributeValue.setOverrideAddress(fakeRequestAddress);
  }
}
