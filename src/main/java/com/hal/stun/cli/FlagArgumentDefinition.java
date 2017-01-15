package com.hal.stun.cli;

import java.util.Map;

public class FlagArgumentDefinition extends ArgumentDefinition<Boolean> {
    public FlagArgumentDefinition(String key, String shortKey, boolean defaultValue, String description) {
        this.valueType = Boolean.class;
        this.key = key;
        this.shortKey = shortKey;
        this.defaultValue = defaultValue;
        this.description = description;
    }

    public FlagArgumentDefinition(String key, String shortKey, ConditionalValue<Boolean> conditionalValue, String description) {
        this.valueType = Boolean.class;
        this.key = key;
        this.shortKey = shortKey;
        this.conditionalValue = conditionalValue;
        this.description = description;
    }

    public Argument parse(String _stringValue) throws ArgumentParseException {
        return new Argument(true);
    }

    public Argument getDefaultArgument(Map<String, Argument> otherArgs) {
        if (conditionalValue != null) {
            return new DefaultArgument(conditionalValue.getValue(otherArgs));
        } else {
            return new DefaultArgument(defaultValue);
        }
    }
}
