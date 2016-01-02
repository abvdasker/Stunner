package com.hal.stun.message;

import org.junit.Test;

public class MagicCookieTest {
  @Test(expected = IndexOutOfBoundsException.class)
  public void getByteTooLarge() throws IndexOutOfBoundsException {
    MagicCookie.getByte(5);
  }
}
