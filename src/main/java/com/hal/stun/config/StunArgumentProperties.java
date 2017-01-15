package com.hal.stun.config;

import com.hal.stun.cli.Argument;

import java.util.HashMap;
import java.util.Map;

import static com.hal.stun.cli.ArgumentParser.TCP_KEY;
import static com.hal.stun.cli.ArgumentParser.UDP_KEY;
import static com.hal.stun.cli.ArgumentParser.TCP_PORT_KEY;
import static com.hal.stun.cli.ArgumentParser.UDP_PORT_KEY;
import static com.hal.stun.cli.ArgumentParser.THREADS_KEY;

public class StunArgumentProperties extends StunProperties {

    private static final Map<String, String> ARGUMENTS_TO_PROPERTIES = new HashMap<String, String>() {{
        put(TCP_KEY, TCP_SERVE_PROPERTY);
        put(UDP_KEY, UDP_SERVE_PROPERTY);
        put(TCP_PORT_KEY, TCP_PORT_PROPERTY);
        put(UDP_PORT_KEY, UDP_PORT_PROPERTY);
        put(THREADS_KEY, THREADS_PROPERTY);
    }};

    private StunArgumentProperties(StunProperties defaults) {
        super(defaults);
    }

    public static StunArgumentProperties build(
            Map<String, Argument> parsedArguments,
            StunProperties defaults
    ) {
        StunArgumentProperties properties = new StunArgumentProperties(defaults);
        for (Map.Entry<String, String> entry : ARGUMENTS_TO_PROPERTIES.entrySet()) {
            Argument parsedArg = parsedArguments.get(entry.getKey());
            if (parsedArg != null && parsedArg.wasSet()) {
                String value = parsedArg.toString();
                properties.setProperty(entry.getValue(), value);
            }
        }
        return properties;
    }
}
