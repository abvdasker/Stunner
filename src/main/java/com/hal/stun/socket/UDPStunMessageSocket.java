package com.hal.stun.socket;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddres;

public class UDPStunMessageSocket extends StunMessageSocket {

  private static final int MAX_PACKET_SIZE_BYTES = 1280; // MTU 

  private final DatagramSocket socket;

  private void setupSocket() {
    socket = new DatagramSocket(port);
  }

  private NetworkMessage listen() {
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

  private boolean transmit(NetworkMessage message) {
    byte[] messageData = message.getData();
    DatagramPacket packet = new DatagramPacket(message.getData(), message.getAddress(), message.getPort());
    socket.send(packet);
    return true;
  }
}
