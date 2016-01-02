package com.hal.stun.message;

import org.junit.Test;
import org.junit.Assert;

public class MagicCookieTest {
  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetByteTooLarge() throws IndexOutOfBoundsException {
    MagicCookie.getByte(4);
  }

  @Test
  public void testGetByte() {
    byte expectedByte = (byte) 0x12;
    byte actualByte = MagicCookie.getByte(2);

    Assert.assertEquals("correctly extracts magic cookie byte 2", expectedByte, actualByte);
  }

  @Test
  public void testGetTyopTwoBytes() {
    short expected = (short) 0x2112;
    short actual = MagicCookie.getTopTwoBytes();

    Assert.assertEquals("correctly extracts top two bytes as short",
                        expected, actual);
  }
}
