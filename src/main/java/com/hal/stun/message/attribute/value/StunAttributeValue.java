package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;

public abstract class StunAttributeValue {

  protected byte[] value;
  public StunAttributeValue(byte[] value) throws StunAttributeValueParseException {
    this.value = value;
    parseValueBytes();
    validate();
  }

  private void validate() throws StunAttributeValueParseException {
    if (!isValid()) {
      String attributeValueHex = StunMessageUtils.convertByteArrayToHex(value);
      throw new StunAttributeValueParseException("could not parse attribute value " + attributeValueHex);
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
  protected abstract void parseValueBytes() throws StunAttributeValueParseException;
  protected abstract boolean isValid();
}
