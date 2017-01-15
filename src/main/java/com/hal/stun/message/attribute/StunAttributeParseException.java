package com.hal.stun.message.attribute;

import com.hal.stun.message.StunParseException;

public class StunAttributeParseException extends StunParseException {

    public StunAttributeParseException(String message) {
        super(message);
    }

    public StunAttributeParseException(String message, Exception exception) {
        super(message, exception);
    }

}
