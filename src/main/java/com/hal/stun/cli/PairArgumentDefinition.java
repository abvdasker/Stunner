package com.hal.stun.cli;

import java.lang.reflect.Constructor;

import java.lang.reflect.InvocationTargetException;

public class PairArgumentDefinition<T> extends ArgumentDefinition<T> {
  public PairArgumentDefinition(Class<T> valueType, String key, String shortKey, T defaultValue, String description) {
    this.valueType = valueType;
    this.key = key;
    this.shortKey = shortKey;
    this.defaultValue = defaultValue;
    this.description = description;
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
