package com.hal.stun.cli;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class Help {
  /*  private static final String[] HELP_TEXT = {
    "  --tcp, -tcp  //  Run TCP STUN server, can be used with --udp",
    "  --udp, -udp  //  Run UDP STUN server, can be used with --tcp",
    "  --tcpport, -tport  //  Port on which to bind the TCP server",
    "  --udpport, -uport  //  Port on which to bind the TCP server",
    "  --help, -h  //  Display usage info",
    "  --threads, -t // Number of theads to use in handler threadpool"
    };*/
  private static final String HELP_HEADER = "\nUsage:\n\n$> java -jar Stunner.jar\n";

  public static String getHelpText() {
    Set<ArgumentDefinition> definitions = ArgumentParser.getDefinitionSet();
    List<String> helpStrings = new ArrayList<String>(definitions.size());
    for (ArgumentDefinition definition : definitions) {
      helpStrings.add(definition.getHelp());
    }
    String joinedHelpText = join("\n", helpStrings);
    return HELP_HEADER + joinedHelpText;
  }

  private static String join(String inter, List<String> strings) {
    StringBuffer joinedString = new StringBuffer();
    for (int i = 0; i < strings.size() - 1; i++) {
      joinedString.append(strings.get(i));
      joinedString.append(inter);
    }
    joinedString.append(strings.get(strings.size() - 1));
    joinedString.append('\n');
    return joinedString.toString();
  }
}
