package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunParseException;
import java.util.zip.CRC32;

public class FingerprintStunAttributeValue extends StunAttributeValue {

  private static final long XOR_VALUE = 0x5354554e;
  private static final int VALUE_SIZE_BYTES = 4;

  public FingerprintStunAttributeValue(byte[] value) throws StunParseException {
    super(value);
  }

  public boolean isValid() {
    return value == VALUE_SIZE_BYTES;
  }

  public void parseValueBytes() throws StunParseException {
  }

  public boolean verifyMessageIntegrity(byte[] messageBytes) {
    CRC32 crc = new CRC32();
    byte[] xORedMessageBytes
    byte result = crc.update(messageBytes);
    // TODO: how to compute this XOR since messageBytes is so much larger?
  }
}
