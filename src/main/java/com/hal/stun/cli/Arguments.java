package com.hal.stun.cli;

import java.util.Map;

public class Arguments {

  private Map<String, String> argumentMap;
  public Arguments(Map<String, String> argumentMap) {
    this.argumentMap = argumentMap;
  }

  public boolean getBoolean(String name) {
    return Boolean.parseBoolean(getString(name));
  }

  public int getInt(String name) {
    return Integer.parseInt(getString(name));
  }

  public String getString(String name) {
    return argumentMap.get(name);
  }
}
