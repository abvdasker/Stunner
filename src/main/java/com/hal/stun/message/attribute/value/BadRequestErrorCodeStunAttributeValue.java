package com.hal.stun.message.attribute.value;

public class BadRequestErrorCodeStunAttributeValue extends ErrorCodeStunAttributeValue {
    public BadRequestErrorCodeStunAttributeValue(String reason) throws StunAttributeValueParseException {
        super(400, reason);
    }
}
