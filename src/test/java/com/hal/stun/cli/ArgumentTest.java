package com.hal.stun.cli;

import org.junit.Test;
import org.junit.Assert;

public class ArgumentTest {
  @Test
  public void testGetInt() {
    Argument argument = new Argument(123);

    Assert.assertEquals("returns the wrapped integer",
                        123,
                        argument.getInt().intValue());
  }

  @Test(expected = ClassCastException.class)
  public void testGetIntFail() {
    new Argument("test").getInt();
  }

  @Test
  public void testGetBoolean() {
    Argument argument = new Argument(true);

    Assert.assertEquals("returns the wrapped boolean",
                        true,
                        argument.getBoolean());
  }

  @Test(expected = ClassCastException.class)
  public void testGetBooleanFail() {
    new Argument("blah").getBoolean();
  }
}
