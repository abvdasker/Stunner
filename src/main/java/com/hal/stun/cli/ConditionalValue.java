package com.hal.stun.cli;

import java.util.Map;

public interface ConditionalValue<T> {
    public T getValue(Map<String, Argument> otherArgs);
}
