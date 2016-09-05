package com.hal.stun.cli;

import org.junit.Test;
import org.junit.Assert;

public class FlagArgumentDefinitionTest {
  @Test
  public void testGetDefaultArgument() {
    ArgumentDefinition<Boolean> definition
      = new FlagArgumentDefinition("--key",
                                   "-k",
                                   false,
                                   "test thing");

    Argument defaultArgument = definition.getDefaultArgument();
    Assert.assertEquals("returns false as default argument value",
                        false,
                        defaultArgument.getBoolean());
  }
  @Test
  public void testParse() throws ArgumentParseException {
    ArgumentDefinition<Boolean> definition
      = new FlagArgumentDefinition("--key",
                                   "-k",
                                   false,
                                   "test thing");

    Argument argument = definition.parse(null);
    Assert.assertEquals("returns true",
                        true,
                        argument.getBoolean());
      
  }
}
