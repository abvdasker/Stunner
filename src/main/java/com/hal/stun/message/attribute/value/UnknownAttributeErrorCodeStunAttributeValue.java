package com.hal.stun.message.attribute.value;

public class UnknownAttributeErrorCodeStunAttributeValue extends ErrorCodeStunAttributeValue {
    public UnknownAttributeErrorCodeStunAttributeValue(String reason) throws StunAttributeValueParseException {
        super(420, reason);
    }
}
