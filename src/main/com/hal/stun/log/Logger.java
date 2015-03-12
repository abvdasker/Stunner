package com.hal.stun.log;

import java.io.OutputStream;

import java.io.IOException;

public class Logger {
  
  private OutputStream out;
  
  public Logger(OutputStream out) {
    if (out == null) {
      throw new NullPointerException();
    }
    this.out = out;
  }
  
  public Logger() {
    this.out = System.out;
  }
  
  public void print(String string) throws IOException {
    out.write(string.getBytes());
    out.flush();
  }
  
}