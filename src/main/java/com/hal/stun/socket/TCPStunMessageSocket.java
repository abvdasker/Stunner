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
    InputStream input = connection.getInputStream();

    byte[] requestData = getRequestData(input);
    InetAddress address = connection.getInetAddress();
    int port = connection.getPort();
    InetSocketAddress socketAddress = new InetSocketAddress(address, port);

    NetworkMessage request = new NetworkMessage(socketAddress, requestData);
    NetworkMessage response = handler.handle(request);
    transmit(connection, response);
    connection.close();
  }

  private void transmit(Socket connection, NetworkMessage response) throws IOException {
    OutputStream output = connection.getOutputStream();
    output.write(response.getData());
    output.flush();
  }

  private byte[] getRequestData(InputStream input) throws IOException {
    if (!(input instanceof BufferedInputStream)) {
      input = new BufferedInputStream(input);
    }
    ByteBuffer byteBuffer = ByteBuffer.allocate(MAX_PACKET_SIZE_BYTES);
    byte inputByte;
    while((inputByte = (byte) input.read()) != -1) {
      byteBuffer.put(inputByte);
    }

    input.close();
    byteBuffer.compact();
    return byteBuffer.array();
  }

}
