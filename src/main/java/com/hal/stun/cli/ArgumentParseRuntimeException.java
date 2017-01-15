package com.hal.stun.cli;

public class ArgumentParseRuntimeException extends RuntimeException {
    public ArgumentParseRuntimeException(Exception exception) {
        super(exception);
    }
}
