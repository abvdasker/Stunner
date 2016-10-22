package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;
import com.hal.stun.message.StunParseException;

public abstract class StunAttributeValue {

  protected byte[] value;
  public StunAttributeValue(byte[] value) throws StunParseException {
    this.value = value;
    parseValueBytes();
    validate();
  }

  private void validate() throws StunParseException {
    if (!isValid()) {
      String attributeValueHex = StunMessageUtils.convertByteArrayToHex(value);
      throw new StunParseException("could not parse attribute value " + attributeValueHex);
    }
  }

  public byte[] getBytes() {
    return value;
  }

  public byte[] getPaddedBytes() {
    int size = StunMessageUtils.nextMultipleOfFour(value.length);
    byte[] paddedBytes = new byte[size];
    System.arraycopy(value, 0, paddedBytes, 0, value.length);
    return paddedBytes;
  }

  public String toString() {
    return StunMessageUtils.formatByteArray(value);
  }

  // TODO: make exception more specific
  protected abstract void parseValueBytes() throws StunParseException;
  protected abstract boolean isValid();
}
