package com.hal.stun.message.errorattributefactory;

import com.hal.stun.message.attribute.StunAttribute;
import com.hal.stun.message.attribute.AttributeType;
import com.hal.stun.message.attribute.value.ServerErrorErrorCodeStunAttributeValue;
import com.hal.stun.message.StunParseException;

import java.util.List;
import java.util.ArrayList;

public class ServerErrorAttributeFactory implements ErrorAttributeFactory {

    private Exception exception;

    public ServerErrorAttributeFactory(Exception exception) {
        this.exception = exception;
    }

    public List<StunAttribute> build() {
        List<StunAttribute> attributes = new ArrayList<>();
        ServerErrorErrorCodeStunAttributeValue value;
        try {
            value = new ServerErrorErrorCodeStunAttributeValue(exception.getMessage());
        } catch (StunParseException attributeValueException) {
            throw new RuntimeException("failed to build server error code exception", attributeValueException);
        }
        StunAttribute attribute = new StunAttribute(AttributeType.ERROR_CODE,
                value);
        attributes.add(attribute);
        return attributes;
    }
}
