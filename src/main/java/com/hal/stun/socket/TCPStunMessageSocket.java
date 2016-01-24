package com.hal.stun.socket;

import java.net.ServerSocket;
import java.net.Socket;

public class TCPStunMessageSocket {

  private ServerSocket socket;

  public TCPStunMessageSocket() {
    super();
  }

  public TCPStunMessageSocket(int port) {
    super(port);
  }

  protected void setupSocket() throws IOException {
    socket = new ServerSocket(port);
  }

  public NetworkMessage listen() throws IOException {
    Socket connectionSocket = socket.accept();
  }

  public boolean transmit(NetworkMessage message) throws IOException {
  }

}
