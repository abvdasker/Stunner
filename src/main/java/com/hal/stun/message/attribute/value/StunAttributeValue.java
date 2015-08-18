package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;
import com.hal.stun.message.StunParseException;

public abstract class StunAttributeValue {
  
  private String attributeValueHex;
  protected byte[] valueBytes;
  public StunAttributeValue(String attributeValueHex) throws StunParseException {
    this.attributeValueHex = attributeValueHex;
    this.valueBytes = StunMessageUtils.convertHexToByteArray(attributeValueHex);
    parseValueBytes();
    validate();
  }
  
  public String getHexValue() {
    return attributeValueHex;
  }
  
  private void validate() throws StunParseException {
    if (!isValid()) {
      throw new StunParseException("could not parse attribute value " + attributeValueHex);
    }
  }
  
  protected abstract void parseValueBytes() throws StunParseException;
  protected abstract boolean isValid();
  
}