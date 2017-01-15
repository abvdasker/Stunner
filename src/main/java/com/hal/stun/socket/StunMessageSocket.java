package com.hal.stun.socket;

import java.io.IOException;

public abstract class StunMessageSocket {

    public static final int DEFAULT_PORT = 3478;
    public static final int MAX_PACKET_SIZE_BYTES = 1280; // MTU

    protected final int port;

    public StunMessageSocket(int port) throws IOException {
        this.port = port;
        setupSocket();
    }

    public StunMessageSocket() throws IOException {
        this.port = DEFAULT_PORT;
        setupSocket();
    }

    protected abstract void setupSocket() throws IOException;

    /*
     * UDP does not need to maintain connection between listen and transmit but TCP does...
     *
     * Need a method which:
     * 1. connects,
     * 2. receives a message,
     * 3. yields to callback which returns the response data
     * 4. transmits the response data to the client
     */
    public abstract void handle(StunHandler handler) throws IOException;
}
