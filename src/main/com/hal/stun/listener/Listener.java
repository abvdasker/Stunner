package com.hal.stun.listener;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.io.IOException;

public class Listener {
  
  private int port;
  
  private ServerSocket serverSocket;
  private Socket clientSocket;
  
  private BufferedInputStream in;
  private BufferedOutputStream out;
  
  public Listener(int port) {
    this.port = port;
  }
  
  public InputStream listen() throws IOException {
    serverSocket = new ServerSocket(port);
    clientSocket = serverSocket.accept();
    in = new BufferedInputStream(clientSocket.getInputStream());
    out = new BufferedOutputStream(clientSocket.getOutputStream());
    return in;
  }
  
  public void say(byte[] output) throws IOException {
    out.write(output);
  }
  
}