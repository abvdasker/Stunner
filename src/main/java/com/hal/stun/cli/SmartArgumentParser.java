package com.hal.stun.cli;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class SmartArgumentParser {

  public static Map<String, Argument> parse(String[] args) throws ArgumentParseException {
    Map<String, ArgumentDefinition> definitions = buildDefinitions();
    Map<String, Argument> values = new HashMap<String, Argument>();
    List<String> argList = Arrays.asList(args);

    while (!argList.isEmpty()) {
      String argument = argList.remove(0);
      ArgumentDefinition definition = definitions.get(argument);
      if (definition == null) {
        throw new ArgumentParseException("Unrecognized argument \"" + argument);
      }
      String value;
      if (definition instanceof FlagArgumentDefinition) {
        value = null;
      } else {
        value = argList.remove(0);
      }
      values.put(definition.getKey(), definition.parse(value));
    }
    addDefaults(values, definitions);

    return null;
  }

  private static Map<String, ArgumentDefinition> buildDefinitions() {
    Map<String, ArgumentDefinition> definitions = new HashMap<String, ArgumentDefinition>();
    addDefinition(definitions,
                  new FlagArgumentDefinition("--tcp",
                                             "-tcp",
                                             true,
                                             "Run TCP STUN Server (can be used with --udp)")
                  );
    addDefinition(definitions,
                  new FlagArgumentDefinition("--udp",
                                             "-udp",
                                             false,
                                             "Run UDP STUN Server (can be used with --tcp)")
                  );
    addDefinition(definitions,
                  new PairArgumentDefinition<Integer>(Integer.class,
                                                      "--tcpport",
                                                      "-tport",
                                                      8000,
                                                      "Port on which to bind the TCP server")
                  );
    addDefinition(definitions,
                  new PairArgumentDefinition<Integer>(Integer.class,
                                                      "--udpport",
                                                      "-uport",
                                                      8001,
                                                      "Port on which to bind the UDP server")
                  );
    addDefinition(definitions,
                  new FlagArgumentDefinition("--help",
                                             "-h",
                                             false,
                                             "Show help")
                  );
    addDefinition(definitions,
                  new PairArgumentDefinition<Integer>(Integer.class,
                                                      "--threads",
                                                      "-t",
                                                      2,
                                                      "Number of threads to use in handler threadpool")
                  );

    return definitions;
  }

  private static void addDefinition(Map<String, ArgumentDefinition> definitions, ArgumentDefinition definition) {
    definitions.put(definition.getKey(), definition);
    definitions.put(definition.getShortKey(), definition);
  }

  private static void addDefaults(Map<String, Argument> arguments, Map<String, ArgumentDefinition> definitions) {
    Set<ArgumentDefinition> dedupedDefinitions = new HashSet<ArgumentDefinition>(definitions.values());
    for (ArgumentDefinition definition : dedupedDefinitions) {
      if (definition.hasDefault() && !arguments.containsKey(definition.getKey())) {
        arguments.put(definition.getKey(), definition.getDefaultArgument());
      }
    }
  }
}
