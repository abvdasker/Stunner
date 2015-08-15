package com.hal.stun.message.attribute.value;

import com.hal.message.StunMessageUtils;

public abstract class StunAttributeValue {
  
  private String attributeValueHex;
  protected byte[] valueBytes;
  public StunAttributeValue(String attributeValueHex) {
    this.attributeValueHex = attributeValueHex;
    this.valueBytes = StunMessageUtils.convertHexToByteArray(valueHex);
    validate();
  }
  
  private void validate() throws StunParseException {
    if (!isValid()) {
      throw new StunParseException("could not parse attribute value " + attributeValueHex);
    }
  }
  
  private abstract boolean isValid();
  
}