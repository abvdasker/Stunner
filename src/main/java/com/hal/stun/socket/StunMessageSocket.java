package com.hal.stun.socket;

public abstract class StunMessageSocket {

  private static final int DEFAULT_PORT = 3478;

  protected final int port;

  public StunListener(int port) {
    this.port = port;
    setupSocket();
  }

  public StunListener() {
    this.port = DEFAULT_PORT;
  }

  private abstract void setupSocket();

  private abstract NetworkMessage listen();

  private abstract boolean transmit(NetworkMessage message);
}
