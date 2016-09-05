package com.hal.stun.cli;

import org.junit.Test;
import org.junit.Assert;

import java.util.Map;

public class FlagArgumentDefinitionTest {
  @Test
  public void testGetDefaultArgument() {
    ArgumentDefinition<Boolean> definition
      = new FlagArgumentDefinition("--key",
                                   "-k",
                                   false,
                                   "test thing");

    Argument defaultArgument = definition.getDefaultArgument(null);
    Assert.assertEquals("returns false as default argument value",
                        false,
                        defaultArgument.getBoolean());
  }

  @Test
  public void testGetDefaultArgumentWithCondition() {
    ArgumentDefinition<Boolean> definition
      = new FlagArgumentDefinition("--key",
                                   "-k",
                                   new ConditionalValue<Boolean>() {
                                     public Boolean getValue(Map<String, Argument> _otherArgs) {
                                       return true;
                                     }
                                   },
                                   "test thing");

    Argument defaultArgument = definition.getDefaultArgument(null);
    Assert.assertEquals("returns true as default argument value from condition",
                        true,
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
