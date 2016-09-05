package com.hal.stun.cli;

import org.junit.Test;
import org.junit.Assert;

public class PairArgumentDefinitionTest {
  @Test
  public void testGetDefaultArgument() {
    ArgumentDefinition<Integer> definition
      = new PairArgumentDefinition<Integer>(Integer.class,
                               "--number",
                               "-n",
                               123,
                               "test number");
    Argument defaultArgument = definition.getDefaultArgument();
    Assert.assertEquals("returns the default integer argument",
                        123,
                        defaultArgument.getInt().intValue());
  }

  @Test
  public void testParse() throws ArgumentParseException {
    ArgumentDefinition<Integer> definition
      = new PairArgumentDefinition<Integer>(Integer.class,
                               "--number",
                               "-n",
                               123,
                               "test number");
    Argument argument = definition.parse("456");
    Assert.assertEquals("returns the default integer argument",
                        456,
                        argument.getInt().intValue());
  }
}
