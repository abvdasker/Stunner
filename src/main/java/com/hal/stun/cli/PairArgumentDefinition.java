package com.hal.stun.cli;

import java.lang.reflect.Constructor;

import java.lang.reflect.InvocationTargetException;

public class PairArgumentDefinition<T> extends ArgumentDefinition<T> {

  protected static int valueLength = 1;

  public PairArgumentDefinition(Class<T> valueType, String key, String description) {
    this.key = key;
    this.shortKey = "-" + key;
    this.description = description;
    this.valueType = valueType;
  }

  public PairArgumentDefinition(Class<T> valueType, String key, String shortKey, String description) {
    this.key = key;
    this.shortKey = shortKey;
    this.description = description;
    this.valueType = valueType;
  }

  public PairArgumentDefinition(Class<T> valueType, String key, String shortkey, T defaultValue, String description) {
    this.key = key;
    this.shortKey = shortKey;
    this.defaultValue = defaultValue;
    this.description = description;
    this.valueType = valueType;
  }

  public Argument parse(String stringValue) throws ArgumentParseException {
    if (stringValue == null) {
      throw new ArgumentParseException("argument " + key + " requires a value");
    }

    T value = null;

    try {
      Constructor<T> constructor = valueType.getConstructor(String.class);
      value = constructor.newInstance(stringValue);
    } catch (InvocationTargetException exception) {
      throw new ArgumentParseException("could not parse value \"" + stringValue +
                                       "\" for argument " + key, exception.getCause());
    } catch (Exception exception) {
      throw new ArgumentParseRuntimeException(exception);
    }
    return new Argument(value);
  }

  public Argument getDefaultArgument() {
    return new Argument(defaultValue);
  }
}
