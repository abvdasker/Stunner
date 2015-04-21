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
  
  public Listener(int port) throws IOException {
    this.port = port;
    serverSocket = new ServerSocket(port);  
  }
  
  /* 
   * TODO: create listener loop. Listen gets passed in the application and calls the
   * app's handler method every time a new request comes in. The handler method will
   * return the response which gets written through the listener's output stream back to the
   * client.
   */ 
  public InputStream listen() throws IOException {
    clientSocket = serverSocket.accept();
    in = new BufferedInputStream(clientSocket.getInputStream());
    out = new BufferedOutputStream(clientSocket.getOutputStream());
    return in;
  }
  
  public void say(byte[] output) throws IOException {
    out.write(output);
  }

  public void refresh() {
    
  }
  
}