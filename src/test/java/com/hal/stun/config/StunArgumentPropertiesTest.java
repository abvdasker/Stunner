package com.hal.stun.config;

import com.hal.stun.cli.Argument;
import com.hal.stun.cli.ArgumentParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class StunArgumentPropertiesTest {

    @Test
    public void testGetTCPPort() {
        Map<String, Argument> parsedArgs = new HashMap<>();
        Argument arg = new Argument(7);
        parsedArgs.put(ArgumentParser.TCP_PORT_KEY, arg);
        StunProperties properties = StunDefaultProperties.build();
        StunArgumentProperties.update(parsedArgs, properties);
        Assert.assertEquals(
                "Should override default property using arguments",
                7,
                properties.getTCPPort());
    }
}
