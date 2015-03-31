package com.hal.stun.message;

import com.hal.stun.message.StunParseException;

public class StunMessage {

    private static final int HEADER_SIZE = 20;
    private static final short BINDING_METHOD = 0b000000000001;

    private byte[] messageBytes;
    private MessageClass messageClass;
    private short method; // 12 bits; binding method is 0b000000000001

    // also add message length, magic cookie and transaction ID fields

    public StunMessage(byte[] message) {
	this.messageBytes = message;
	parse(messageBytes);
    }

    private void parse(byte[] message) throws StunParseException {
	byte[] header = getHeaderBytes(message);
	parseHeader(header);
    }

    private byte[] getHeaderBytes(byte[] message) throws StunParseException {
	if (message.length < HEADER_SIZE) {
	    throw new StunParseException("message was smaller than header size. Header must be 20 bytes");
	}
	byte[] header = new byte[HEADER_SIZE];
	for (int i = 0; i < header.length; i++) {
	    header[i] = message[i];
	}
	return header;
    }

    private void parseHeader(byte[] header) throws StunParseException {
	byte firstByte = header[0];
	if (firstByte>>>6 != 0) {
	    throw new StunParseException("first two bits of header were not zero");
	}
	byte messageClassBits = getMessageClassBits(header);
	messageClass = MessageClass.fromByte(messageClassBits);
	
    }

    private byte getMessageClassBits(byte[] header) {
	byte messageClassBits = 0b0;
	byte topBit = header[0];
	byte lowerBit = header[1];
	topBit &= 0b00000001;
	lowerBit = lowerBit & 0b00010000;
	topBit <<= 1;
	lowerBit >>>= 4;
	return topBit | lowerBit;
    }

    private short getMessageMethod(byte[] header) {
	short topBits = header[0];
	topBits >>>= 1;
	topBits <<= 9;

	short lowerBits = header[1];

	short fullBits = topBits | lowerBits;
	fullBits &= 
    }

}