package com.hal.stun.message.attribute.value;

public class StaleNonceErrorCodeStunAttributeValue extends ErrorCodeStunAttributeValue {
    public StaleNonceErrorCodeStunAttributeValue(String reason) throws StunAttributeValueParseException {
        super(438, reason);
    }
}
