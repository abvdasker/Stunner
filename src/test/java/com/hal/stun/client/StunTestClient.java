package com.hal.stun.client;

import java.net.InetSocketAddress;

import java.io.IOException;

public abstract class StunTestClient {
  protected static final int DEFAULT_TIMEOUT_MILLIS = 1000;

  protected InetSocketAddress serverAddress;

  public StunTestClient(InetSocketAddress serverAddress) {
    this.serverAddress = serverAddress;
  }

  abstract public byte[] send(byte[] data) throws IOException;

  abstract public void close();
}
