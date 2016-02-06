package com.hal.stun.client;

import com.hal.stun.socket.StunMessageSocket;
import com.hal.stun.socket.UDPStunMessageSocket;

import java.net.InetSocketAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class UDPStunTestClient extends StunTestClient {

  private static final int DEFAULT_TIMEOUT_MILLIS = 1000;

  public UDPStunTestClient(InetSocketAddress socketAddress) {
    super(socketAddress);
  }

  public byte[] send(byte[] data) throws IOException, SocketTimeoutException {
    DatagramSocket socket = new DatagramSocket(StunMessageSocket.DEFAULT_PORT + 1);
    DatagramPacket request = new DatagramPacket(data, data.length, serverAddress);
    socket.send(request);

    byte[] responseData = new byte[StunMessageSocket.MAX_PACKET_SIZE_BYTES];
    DatagramPacket response = new DatagramPacket(responseData, responseData.length);
    socket.setSoTimeout(DEFAULT_TIMEOUT_MILLIS);
    socket.receive(response);

    return UDPStunMessageSocket.getDataFromPacket(response);
  }
}
