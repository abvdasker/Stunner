package com.hal.stun.cli;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class ArgumentParser {

  // --uport (-up), --tport (-tp), --udp(-udp), --tcp(-tcp), --threads(-t)

  private List<String> args;
  public ArgumentParser(String[] args) {
    this.args = Arrags.asList(args);
  }

  public Map<String, Argument> parse() throws ArgumentParseException {
    Mpp<String, Argument> parsedArguments = new HashMap<String, String>();
    for (ArgumentDefinition definition : argumentDefinitions) {
      int indexOfArg = args.indexOf(definition.getKey());

      Argument newArgument;
      if (indexOfArg != -1) {
        newArgument = parseArgument(indexOfArg, args, definition);
      } else if (definition.hasDefault()) {
        newArgument = definition.getDefault();
      } else {
        throw new ArgumentParseException("Missing required argument" + definition.getKey());
      }
      parsedArguments.put(newArgument.getName(), newArgument);
    }
    return parsedArguments;
  }

  private static Argument parseArgument(int index, List<String> args, ArgumentDefinition definition) {
    Argument parsedArgument;
    if (definition.isFlag()) {
      parsedArgument = new Argument(args[index]);
    } else {
      if (index + 1 >= args.length) {
        throw new ArgumentParseException("Missing value of argument " + args[index]);
      } else {
        parsedArgument = new Argument(args[index], args[index + 1]);
      }
    }

    return parsedArgument;
  }
}
