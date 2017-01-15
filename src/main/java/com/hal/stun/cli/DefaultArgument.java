package com.hal.stun.cli;

public class DefaultArgument extends Argument {
    public DefaultArgument(Object value) {
        super(value);
    }

    @Override
    public boolean wasSet() {
        return false;
    }
}
