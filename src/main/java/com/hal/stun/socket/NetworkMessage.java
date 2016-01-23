package com.hal.stun.socket;

import java.net.InetSocketAddress;
import java.net.InetAddress;

public class NetworkMessage {

  private final byte[] data;
  private final InetSocketAddress socketAddress;

  public NetworkMessage(InetSocketAddress socketAddress, byte[] data) {
    this.data = data;
    this.socketAddress = socketAddress;
  }

  public byte[] getData() {
    return data;
  }

  public InetSocketAddress getSocketAddress() {
    return socketAddress;
  }

  public InetAddress getAddress() {
    return socketAddress.getAddress();
  }

  public int getPort() {
    return socketAddress.getPort();
  }
}
