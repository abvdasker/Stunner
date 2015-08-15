package com.hal.stun.message.attribute.value;

public abstract class StunAttributeValue {
  
  private String attributeValueHex;
  public StunAttributeValue(String attributeValueHex) {
    this.attributeValueHex = attributeValueHex;
  }
  
  
}