package com.hal.stun.config;

import com.hal.stun.cli.Argument;

import java.util.Map;

public class StunConfiguration {

    public static StunProperties getConfig(Map<String, Argument> parsedArgs) {
        StunProperties propertiesConfiguration = StunProperties.build();
        return StunArgumentProperties.build(parsedArgs, propertiesConfiguration);
    }

    public static StunProperties getConfig() {
        return StunProperties.build();
    }
}
