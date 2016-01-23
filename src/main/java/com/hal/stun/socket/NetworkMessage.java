package com.hal.stun.socket;

import java.net.InetSocketAddress;

public NetworkMessage {

  private final byte[] data;
  private final InetSocketAddress socketAddress;

  public NetworkRequest(InetSocketAddress socketAddress, byte[] data) {
    this.data = data;
    this.socketAddress = socketAddress;
  }

  public byte[] getData() {
    return requestData;
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
