package com.hal.stun.message;

import com.hal.stun.message.StunParseException;

public class StunMessage {

    private static final int HEADER_SIZE = 20;
    private static final short BINDING_METHOD = 0b000000000001;
    
    private byte messageClass; // 2 bits
    private short method; // 12 bits; binding method is 0b000000000001
    /*TODO: write enum of message classes
     * 0b00 - requeust
     * 0b01 - indication
     * 0b10 - success response
     * 0b11 - error response
     */

    // also add message length, magic cookie and transaction ID fields

    public StunMessage(byte[] message) {
	
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

	

    }



}