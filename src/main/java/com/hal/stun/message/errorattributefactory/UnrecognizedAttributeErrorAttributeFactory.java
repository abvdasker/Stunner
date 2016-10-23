package com.hal.stun.message.errorattributefactory;

import com.hal.stun.message.attribute.StunAttribute;
import com.hal.stun.message.attribute.AttributeType;
import com.hal.stun.message.attribute.UnrecognizedAttributeTypeException;
import com.hal.stun.message.attribute.value.UnknownAttributesAttributeValue;
import com.hal.stun.message.attribute.value.UnknownAttributeErrorCodeStunAttributeValue;
import com.hal.stun.message.StunParseException;

import java.util.List;
import java.util.ArrayList;

public class UnrecognizedAttributeErrorAttributeFactory implements ErrorAttributeFactory {

  private UnrecognizedAttributeTypeException exception;
  public UnrecognizedAttributeErrorAttributeFactory(UnrecognizedAttributeTypeException exception) {
    this.exception = exception;
  }

  public List<StunAttribute> build() {
    List<StunAttribute> attributes = new ArrayList<StunAttribute>();
    attributes.add(buildUnknownAttributesAttribute(exception));
    attributes.add(buildUnknownAttributeErrorCodeAttribute());
    return attributes;
  }

  private StunAttribute buildUnknownAttributesAttribute(UnrecognizedAttributeTypeException exception) {
    try {
      UnknownAttributesAttributeValue value = new UnknownAttributesAttributeValue(exception.getAttributeType());
      return new StunAttribute(AttributeType.UNKNOWN_ATTRIBUTES,
                               value);
    } catch (StunParseException attributeValueParseException) {
      throw new RuntimeException("Error creating unknown attributes attribute", exception);
    }
  }

  private StunAttribute buildUnknownAttributeErrorCodeAttribute() {
    try {
      UnknownAttributeErrorCodeStunAttributeValue value
        = new UnknownAttributeErrorCodeStunAttributeValue("Unknown comprehension-required attribute type found in list of request attributes");
      return new StunAttribute(AttributeType.ERROR_CODE,
                               value);
    } catch (StunParseException exception) {
      throw new RuntimeException("Error creating unrecognized attribute error code exception", exception);
    }
  }

}
