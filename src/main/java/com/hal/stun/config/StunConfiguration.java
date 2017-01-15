package com.hal.stun.config;

import com.hal.stun.cli.Argument;

import java.util.Map;

public class StunConfiguration {

    private static StunProperties configuration = StunProperties.build();

    public static void update(Map<String, Argument> parsedArgs) {
        StunArgumentProperties.update(parsedArgs, configuration);
    }

    public static StunProperties getConfig() {
        return configuration;
    }
}
