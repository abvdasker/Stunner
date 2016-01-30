package com.hal.stun.client;

import java.net.InetSocketAddress;

import java.io.IOException;

public abstract class StunTestClient {
  protected InetSocketAddress serverAddress;

  public StunTestClient(InetSocketAddress socketAddress) {
    this.serverAddress = socketAddress;
  }

  abstract public byte[] send(byte[] data) throws IOException;
}
