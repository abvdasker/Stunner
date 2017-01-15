package com.hal.stun.message;

public final class MagicCookie {
    public static final int VALUE = 0x2112A442;

    public static short getTopTwoBytes() {
        return (short) (VALUE >>> (2 * 8));
    }

    public static byte getByte(int index) {
        if (index > 3) {
            throw new IndexOutOfBoundsException(index + " is out of bounds for the 4-byte magic cookie");
        }

        byte magicCookieByte = (byte) (VALUE >>> index * 8);
        return magicCookieByte;
    }

    public static byte[] getBytesBigEndian() {
        byte[] cookieBytes = new byte[4];

        for (int i = 0; i < cookieBytes.length; i++) {
            int bigEndianIndex = (cookieBytes.length - 1) - i;
            cookieBytes[bigEndianIndex] = getByte(i);
        }

        return cookieBytes;
    }
}
