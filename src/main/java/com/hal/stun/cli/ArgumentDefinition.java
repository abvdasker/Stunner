package com.hal.stun.cli;

/*
 * 1. Create a hash mapping string keys to argument definitions
 * 2. Iterate over argument string array
 * 3. Match string argument to definition
 * 4. Use argument definition to parse next N items in argument string array
 * 5. Map argument definition to parsed Argument
 * 6. Iterate over argument definitions to get all values including defaults
 */
public abstract class ArgumentDefinition<T> {

  protected String key;
  protected String shortKey;
  protected String description;
  protected T defaultValue;
  protected Class<T> valueType;

  public String getKey() {
    return key;
  }

  public String getShortKey() {
    return shortKey;
  }

  public String getDescription() {
    return description;
  }

  abstract public Argument parse(String stringValue) throws ArgumentParseException;

  abstract public Argument getDefaultArgument();

  public boolean hasDefault() {
    return defaultValue != null;
  }

  /*
   * Argument definition should include a type for the argument
   * Then initialize the argument using the type to set the 
   */

  /*
   * ArgumentDefinition<Integer> httpPortDefinition = new PairArgumentDefinition<Integer>("--httpport", "-hport", 8080);
   * 
   * Integer httpPort = httpPortDefinition.parse(value);
   */
}
