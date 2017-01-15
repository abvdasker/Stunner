package com.hal.stun.config;

import com.hal.stun.cli.Argument;

import java.util.Map;

public class StunConfiguration {

    private static StunProperties configuration = StunProperties.build();

    public static void setConfig(Map<String, Argument> parsedArgs) {
        configuration = StunArgumentProperties.build(parsedArgs, configuration);
    }

    public static StunProperties getConfig() {
        return configuration;
    }
}
