package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;

import java.util.Arrays;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class MappedAddressStunAttributeValue extends StunAttributeValue {

    // 8 bytes OR 20 bytes
    // 0x0001 - type;
    // 0x0000 - length;
    // value
    //   0x00 empty byte
    //   0x00 address family;
    //   0x0000 port;
    //   0x0000 0000 ipv4 address
    //     OR
    //   0x0000 0000 0000 0000 0000 0000 0000 0000 ipv6 address
    //
    protected static final byte IPV4_FAMILY_CODE = (byte) 0x01;
    protected static final byte IPV6_FAMILY_CODE = (byte) 0x02;

    protected static final int ATTRIBUTE_METADATA_SIZE = 4;

    protected static final int IPV4_ADDRESS_SIZE = 4;
    protected static final int IPV6_ADDRESS_SIZE = 16;

    protected static final int IPV4_ATTRIBUTE_SIZE = ATTRIBUTE_METADATA_SIZE + IPV4_ADDRESS_SIZE;
    protected static final int IPV6_ATTRIBUTE_SIZE = ATTRIBUTE_METADATA_SIZE + IPV6_ADDRESS_SIZE;

    private byte addressFamily;
    private short port;
    private InetAddress address;

    public MappedAddressStunAttributeValue(byte[] value) throws StunAttributeValueParseException {
        super(value);
    }

    public MappedAddressStunAttributeValue() throws StunAttributeValueParseException {
        super(null);
    }

    public byte getAddressFamily() {
        return addressFamily;
    }

    public short getPort() {
        return port;
    }

    public InetAddress getAddress() {
        return address;
    }

    protected void parseValueBytes() throws StunAttributeValueParseException {
        addressFamily = value[1];
        port = parsePort();

        byte[] addressBytes = Arrays.copyOfRange(value, 4, value.length);
        try {
            address = InetAddress.getByAddress(addressBytes);
        } catch (UnknownHostException exception) {
            throw new StunAttributeValueParseException("could not parse internet address", exception);
        }
    }

    private short parsePort() {
        short thisPort = value[2];
        thisPort <<= 8;
        thisPort |= (value[3] & StunMessageUtils.MASK);
        return thisPort;
    }

    protected boolean isValid() {
        if (value[0] != 0) {
            return false;
        }
        if (addressFamily == IPV4_FAMILY_CODE && value.length == IPV4_ATTRIBUTE_SIZE) {
            return true;
        } else if (addressFamily == IPV6_FAMILY_CODE && value.length == IPV6_ATTRIBUTE_SIZE) {
            return true;
        }

        return false;
    }

    public String toString() {
        InetSocketAddress addressAndPort
                = new InetSocketAddress(address, port & StunMessageUtils.MASK);
        String addressStringBuffer = super.toString() +
                '\n' +
                addressAndPort.toString();
        return addressStringBuffer;
    }

}
