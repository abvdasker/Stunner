package com.hal.stun.config;

import com.hal.stun.cli.ArgumentDefinition;

import java.util.Map;

public class StunArgumentProperties extends StunProperties {

    private final Map<String, ArgumentDefinition> parsedArguments;

    public StunArgumentProperties(Map<String, ArgumentDefinition> parsedArguments) {
        this.parsedArguments = parsedArguments;
    }
}
