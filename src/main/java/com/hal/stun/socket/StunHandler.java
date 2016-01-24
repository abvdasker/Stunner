package com.hal.stun.socket;

public interface StunHandler {
  public NetworkMessage handle(NetworkMessage request);
}
