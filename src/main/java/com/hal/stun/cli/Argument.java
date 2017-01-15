package com.hal.stun.cli;

public class Argument {
    private Object value;

    public Argument(Object value) {
        this.value = value;
    }

    public Integer getInt() {
        return (Integer) value;
    }

    public Boolean getBoolean() {
        return (Boolean) value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public boolean wasSet() {
        return true;
    }
}
