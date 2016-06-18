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

  public int getInt(String name) throws ArgumentParseException {
    String value = getString(name);
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException exception) {
      throw new ArgumentParseException("argument " + name + " must be an integer, was " + value);
    }
  }

  public String getString(String name) {
    return argumentMap.get(name);
  }
}
