package com.hal.stun.cli;

public class FlagArgumentDefinition extends ArgumentDefinition<Boolean> {
  public FlagArgumentDefinition(String key, String shortKey, boolean defaultValue, String description) {
    this.key = key;
    this.shortKey = shortKey;
    this.defaultValue = defaultValue;
    this.description = description;
    this.valueType = Boolean.class;
  }

  public Argument parse(String _stringValue) throws ArgumentParseException {
    return new Argument(true);
  }

  public Argument getDefaultArgument() {
    return new Argument(defaultValue);
  }
}
