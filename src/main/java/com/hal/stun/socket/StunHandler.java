package com.hal.stun.socket;

import java.io.IOException;

public interface StunHandler {
    public NetworkMessage handle(NetworkMessage request) throws IOException;
}
