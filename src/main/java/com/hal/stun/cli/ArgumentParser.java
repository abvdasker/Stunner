package com.hal.stun.cli;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

public class ArgumentParser {

  public static final int DEFAULT_TCP_PORT = 8000;
  public static final int DEFAULT_UDP_PORT = 8001;

  private List<String> args;
  public ArgumentParser(String[] args) {
    this.args = Arrags.asList(args);
  }

  public Arguments parse() throws ArgumentParseException {
    Map<String, String> parsedArguments = new HashMap<String, String>();
    addValue(parsedArguments, "--tcpport", "-tport", DEFAULT_TCP_PORT + "");
    addValue(parsedArguments, "--udpport", "-uport", DEFAULT_UDP_PORT + "");
    addValue(parsedArguments, "--threads", "-t", "2");
    addFlagValue(parsedArguments, "--udp", "-tcp", "false");
    addFlagValue(parsedArguments, "--tcp", "-tcp", "true");
    return new Arguments(parsedArguments);
  }

  private void addValue(Map<String, String> parsedArguments, String name, String shortName, String defaultValue) throws ArgumentParseException {
    int index = args.indexOf(name);
    if (index == -1) {
      index = args.indexOf(shortName);
    }

    if (index != -1) {
      String value;
      try {
        value = args.get(index + 1);
      } catch (IndexOutOfBoundsException exception) {
        throw new ArgumentParseException("Missing value for arg " + args.get(index));
      }
      parsedArguments.put(name, value);
    } else {
      parsedArguments.put(name, defaultValue);
    }
  }

  private void addFlagValue(Map<String, String> parsedArguments, String name, String shortName, String defaultValue) throws ArgumentParseException {
    int index = args.indexOf(name);
    if (index == -1) {
      index = args.indexOf(shortName);
    }

    if (index != -1) {
      parsedArguments.put(name, "true");
    } else {
      parsedArguments.put(name, defaultValue);
    }
  }
}
