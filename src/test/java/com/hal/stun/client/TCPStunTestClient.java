package com.hal.stun.client;

import com.hal.stun.socket.StunMessageSocket;
import com.hal.stun.message.StunMessageUtils;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.io.InputStream;

import java.io.IOException;

public class TCPStunTestClient extends StunTestClient {

  private static Socket socket;
  public TCPStunTestClient(InetSocketAddress serverAddress) throws IOException {
    super(serverAddress);
    socket = new Socket(serverAddress.getAddress(), serverAddress.getPort());
  }

  public byte[] send(byte[] data) throws IOException {
    OutputStream output = socket.getOutputStream();
    output.write(data);
    output.flush();
    socket.shutdownOutput();

    InputStream input = socket.getInputStream();
    byte[] paddedResponse = new byte[StunMessageSocket.MAX_PACKET_SIZE_BYTES];
    int next;
    int responsePosition = 0;
    while ((next = input.read()) != -1) {
      paddedResponse[responsePosition] = (byte) next;
      responsePosition++;
    }
    byte[] response = new byte[responsePosition];
    System.arraycopy(paddedResponse, 0, response, 0, responsePosition);

    return response;
  }

  public void close() {
    try {
      if (!socket.isClosed()) {
        socket.close();
      }
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
