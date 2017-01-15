package com.hal.stun.cli;

public class ArgumentParseException extends Exception {
    public ArgumentParseException(String message) {
        super(message);
    }

    public ArgumentParseException(String message, Throwable parentException) {
        super(message, parentException);
    }
}
