package com.hal.stun.socket;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import java.io.IOException;

public class UDPStunMessageSocket extends StunMessageSocket {

  private DatagramSocket socket;

  public UDPStunMessageSocket() throws IOException {
    super();
  }

  public UDPStunMessageSocket(int port) throws IOException {
    super(port);
  }

  protected void setupSocket() throws IOException {
    socket = new DatagramSocket(port);
  }

  public void handle(StunHandler handler) throws IOException {
    NetworkMessage request = listen();
    NetworkMessage response = handler.handle(request);
    transmit(response);
  }

  private NetworkMessage listen() throws IOException {
    byte[] packetWrapper = new byte[MAX_PACKET_SIZE_BYTES];
    DatagramPacket packet = new DatagramPacket(packetWrapper, packetWrapper.length);

    socket.receive(packet);

    int dataLengthBytes = packet.getLength();
    byte[] requestData = new byte[dataLengthBytes];
    int offset = packet.getOffset();
    System.arraycopy(packet.getData(), offset, requestData, 0, dataLengthBytes);

    InetSocketAddress socketAddress = new InetSocketAddress(packet.getAddress(), packet.getPort());
    return new NetworkMessage(socketAddress, requestData);
  }

  private boolean transmit(NetworkMessage message) throws IOException {
    byte[] messageData = message.getData();
    DatagramPacket packet = new DatagramPacket(messageData, messageData.length, message.getAddress(), message.getPort());
    socket.send(packet);
    return true;
  }
}
