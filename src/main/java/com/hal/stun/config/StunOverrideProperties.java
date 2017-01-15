package com.hal.stun.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class StunOverrideProperties extends StunProperties {

    private static final String OVERRIDE_FILE = "stunner.properties";

    private StunOverrideProperties(StunProperties defaults) {
        super(defaults);
    }

    public static StunOverrideProperties build(StunProperties defaults) {
        StunOverrideProperties properties = new StunOverrideProperties(defaults);
        try {
            if (canLoad()) {
                properties.load(new FileInputStream(getResourceFilepath()));
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return properties;
    }

    public static boolean canLoad() {
        return new File(getResourceFilepath()).exists();
    }

    public static String getResourceFilepath() {
        try {
            String currentPath = StunOverrideProperties.
                    class.
                    getProtectionDomain().
                    getCodeSource().
                    getLocation().
                    toURI().
                    getPath();
            return currentPath + "/" + OVERRIDE_FILE;
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }
}
