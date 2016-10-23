package com.hal.stun.message.errorattributefactory;

import com.hal.stun.message.attribute.StunAttribute;
import com.hal.stun.message.attribute.AttributeType;
import com.hal.stun.message.attribute.value.BadRequestErrorCodeStunAttributeValue;
import com.hal.stun.message.StunParseException;

import java.util.List;
import java.util.ArrayList;

public class InvalidRequestErrorAttributeFactory implements ErrorAttributeFactory {

  private StunParseException exception;
  public InvalidRequestErrorAttributeFactory(StunParseException exception) {
    this.exception = exception;
  }

  public List<StunAttribute> build() {
    List<StunAttribute> attributes = new ArrayList<StunAttribute>();
    BadRequestErrorCodeStunAttributeValue value;
    try {
      value = new BadRequestErrorCodeStunAttributeValue(exception.getMessage());
    } catch (StunParseException attributeValueParseException) {
      throw new RuntimeException("Error creating error code attribute value");
    }
    StunAttribute attribute = new StunAttribute(AttributeType.ERROR_CODE,
                                                value);
    attributes.add(attribute);
    return attributes;
  }
}

