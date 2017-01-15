package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;

public class UsernameStunAttributeValue extends StunAttributeValue {

    private final static int MAXIMUM_USERNAME_LENGTH_BYTES = 512;

    private String username;

    public UsernameStunAttributeValue(byte[] value) throws StunAttributeValueParseException {
        super(value);
    }

    public String getUsername() {
        return username;
    }

    protected boolean isValid() {
        return value.length <= MAXIMUM_USERNAME_LENGTH_BYTES;
    }

    protected void parseValueBytes() throws StunAttributeValueParseException {
        username = new String(value);
    }

    public String toString() {
        String usernameStringBuffer = "\n" +
                getUsername() +
                '\n' +
                StunMessageUtils.formatByteArray(value);
        return usernameStringBuffer;
    }
}
