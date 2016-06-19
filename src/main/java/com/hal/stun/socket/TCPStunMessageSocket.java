package com.hal.stun.socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.OutputStream;

import java.io.IOException;

public class TCPStunMessageSocket extends StunMessageSocket {

  private ServerSocket socket;

  public TCPStunMessageSocket() throws IOException {
    super();
  }

  public TCPStunMessageSocket(int port) throws IOException {
    super(port);
  }

  protected void setupSocket() throws IOException {
    socket = new ServerSocket(port);
  }

  public void handle(StunHandler handler) throws IOException {
    Socket connection = socket.accept();

    NetworkMessage request = receive(connection);
    NetworkMessage response = handler.handle(request);
    transmit(connection, response);
    if (!connection.isClosed()) {
      connection.close();
    }
  }

  private NetworkMessage receive(Socket connection) throws IOException {
    InputStream input = connection.getInputStream();

    byte[] requestData = getRequestData(input);
    InetAddress address = connection.getInetAddress();
    int port = connection.getPort();
    InetSocketAddress socketAddress = new InetSocketAddress(address, port);
    return new NetworkMessage(socketAddress, requestData);
  }

  private void transmit(Socket connection, NetworkMessage response) throws IOException {
    OutputStream output = connection.getOutputStream();
    output.write(response.getData());
    output.flush();
    connection.shutdownOutput();
  }

  private byte[] getRequestData(InputStream input) throws IOException {
    ByteBuffer byteBuffer = ByteBuffer.allocate(MAX_PACKET_SIZE_BYTES);
    int inputByte;
    while((inputByte = input.read()) != -1) {
      byteBuffer.put((byte) inputByte);
    }

    int bytesRead = byteBuffer.position();
    byte[] request = new byte[bytesRead];
    System.arraycopy(byteBuffer.array(), 0, request, 0, bytesRead);

    return request;
  }

}
