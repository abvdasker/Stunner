package com.hal.stun.config;

import java.util.Properties;

/*
 * TODO: Create an adapter to convert the arguments into properties, then use the 
 * StunOverrideProperties as the default for those.
 *
 * There should be some generic type of stun properties where you only have to override the
 * builder method.
 */
public abstract class StunProperties extends Properties {

    public static final String TCP_SERVE_PROPERTY = "server.tcp.serve";
    public static final String UDP_SERVE_PROPERTY = "server.udp.serve";
    public static final String TCP_PORT_PROPERTY = "server.tcp.port";
    public static final String UDP_PORT_PROPERTY = "server.udp.port";
    public static final String THREADS_PROPERTY = "server.threads";

    protected StunProperties() {
        super();
    }

    protected StunProperties(Properties properties) {
        super(properties);
    }

    public static StunProperties build() {
        StunProperties defaults = StunDefaultProperties.build();
        if (StunOverrideProperties.canLoad()) {
            return StunOverrideProperties.build(defaults);
        } else {
            return defaults;
        }
    }

    public boolean getServeTCP() {
        String serveTCP = getProperty(TCP_SERVE_PROPERTY);
        return Boolean.parseBoolean(serveTCP);
    }

    public boolean getServeUDP() {
        String serveUDP = getProperty(UDP_SERVE_PROPERTY);
        return Boolean.parseBoolean(serveUDP);
    }

    public int getTCPPort() {
        String tcpPort = getProperty(TCP_PORT_PROPERTY);
        return Integer.parseInt(tcpPort);
    }

    public int getUDPPort() {
        String udpPort = getProperty(UDP_PORT_PROPERTY);
        return Integer.parseInt(udpPort);
    }

    public int getThreads() {
        String threads = getProperty(THREADS_PROPERTY);
        return Integer.parseInt(threads);
    }

    public String getSoftwareName() {
        return getProperty("server.software.name");
    }
}
