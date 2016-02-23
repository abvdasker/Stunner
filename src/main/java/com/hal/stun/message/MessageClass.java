package com.hal.stun.message;

public enum MessageClass {

  /*
   * 0b00 - requeust
   * 0b01 - indication
   * 0b10 - success response
   * 0b11 - error response
   */

  REQUEST((byte) 0b00),
  INDICATION((byte) 0b01),
  SUCCESS((byte) 0b10),
  ERROR((byte) 0b11);

  private byte classBits;
  MessageClass(byte classBits) {
    this.classBits = classBits;
  }

  public byte getClassBits() {
    return classBits;
  }

  public static MessageClass fromByte(byte classBits) {
    for (MessageClass messageClass : MessageClass.values()) {
      if (messageClass.getClassBits() == classBits) {
        return messageClass;
      }
    }
    throw new RuntimeException("should never reach here. Invalid classBits specified");
  }


}
