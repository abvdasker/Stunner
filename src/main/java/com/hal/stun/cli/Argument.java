package com.hal.stun.cli;

public class Argument {
  private String name;
  private String value;
  public Argument(String name) {
    this.name = name;
  }

  public Argument(String name, String value) {
    this.name = name;
    this.value = value;;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }
}
