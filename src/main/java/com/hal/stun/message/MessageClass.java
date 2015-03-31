package com.hal.stun.message;

public enum MessageClass {

    /*TODO: write enum of message classes
     * 0b00 - requeust
     * 0b01 - indication
     * 0b10 - success response
     * 0b11 - error response
     */

    REQUEST(0b00);
    INDICATION(0b01);
    SUCCESS(0b10);
    ERROR(0b11);

    private byte classBits;
    public MessageClass(byte classBits) {
	this.classBits = classBits;
    }

    public byte getClassBits() {
	return classBits;
    }

    public static MessageClass fromByte(byte classBits) {
	switch(classBits) {
	case 0b00:
	    return REQUEST;
	case 0b01:
	    return INDICATION; 
	case 0b10:
	    return SUCCESS;
	case 0b11:
	    return ERROR;
	}
    }


}