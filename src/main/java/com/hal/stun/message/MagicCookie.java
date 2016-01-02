package com.hal.stun.message;

public final class MagicCookie {
  public static final int VALUE = 0x2112A442;

  public static short getTopTwoBytes() {
    return (short) (VALUE >>> (2*8));
  }

  public static byte getByte(int index) {
    if (index > 3) {
      throw new IndexOutOfBoundsException(index + " is out of bounds for the 4-byte magic cookie");
    }

    byte magicCookieByte = (byte) (VALUE >>> index * 8);
    return magicCookieByte;
  }
}
