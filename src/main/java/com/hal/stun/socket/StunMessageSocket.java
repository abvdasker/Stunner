package com.hal.stun.socket;

import java.io.IOException;

public abstract class StunMessageSocket {

  private static final int DEFAULT_PORT = 3478;

  protected final int port;

  public StunMessageSocket(int port) throws IOException {
    this.port = port;
    setupSocket();
  }

  public StunMessageSocket() throws IOException {
    this.port = DEFAULT_PORT;
    setupSocket();
  }

  protected abstract void setupSocket() throws IOException;

  public abstract NetworkMessage listen() throws IOException;

  public abstract boolean transmit(NetworkMessage message) throws IOException;
}
