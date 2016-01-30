package com.hal.stun.client;

import java.net.InetSocketAddress;

public abstract class StunTestClient {
  protected InetSocketAddress serverAddress;

  public StunTestClient(InetSocketAddress socketAddress) {
    this.socketAddress = serverAddress;
  }

  abstract public byte[] send(byte[] data) throws IOException;
}
