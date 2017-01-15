package com.hal.stun.config;

import java.io.InputStream;
import java.io.IOException;

public class StunDefaultProperties extends StunProperties {

    private static final String DEFAULT_PROPERTIES = "/resources/default.properties";

    public static StunDefaultProperties build() {
        StunDefaultProperties defaults = new StunDefaultProperties();
        try {
            InputStream propertiesInputStream = StunProperties.class.getResourceAsStream(DEFAULT_PROPERTIES);
            defaults.load(propertiesInputStream);
            propertiesInputStream.close();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return defaults;
    }
}
