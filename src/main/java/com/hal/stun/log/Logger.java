package com.hal.stun.log;

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
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

  public Logger(File file) throws IOException {
    if (!file.exists()) {
      file.createNewFile();
    }

    this.out = new BufferedOutputStream(new FileOutputStream(file));
  }
  
  public void print(String string) throws IOException {
    out.write(string.getBytes());
    out.flush();
  }
  
  public void print(char c) throws IOException {
    out.write(c);
    out.flush();
  }
  
  public void print(int i) throws IOException {
    print(i + "");
  }
  
}