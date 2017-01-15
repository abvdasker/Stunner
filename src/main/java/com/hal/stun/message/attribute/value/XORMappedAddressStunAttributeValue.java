package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;
import com.hal.stun.message.MagicCookie;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class XORMappedAddressStunAttributeValue extends MappedAddressStunAttributeValue {

    private static InetSocketAddress overrideAddress = null;

    public XORMappedAddressStunAttributeValue(byte[] value) throws StunAttributeValueParseException {
        super(value);
    }

    // TODO: need to pass in transaction ID for XOR operation
    public XORMappedAddressStunAttributeValue(InetSocketAddress address, byte[] transactionID) throws StunAttributeValueParseException {
        super(generateFrom(address, transactionID));
    }

    private static byte[] generateFrom(InetSocketAddress address, byte[] transactionID) {
        if (overrideAddress != null) {
            address = overrideAddress;
        }
        InetAddress baseAddress = address.getAddress();
        byte[] addressBytes = baseAddress.getAddress();

        short xPort = generateXPort(address.getPort());
        byte family;
        byte[] xAddress;
        byte[] value;
        if (addressBytes.length == IPV4_ADDRESS_SIZE) {
            family = IPV4_FAMILY_CODE;
            xAddress = generateIPV4XAddress(addressBytes);
            value = new byte[IPV4_ATTRIBUTE_SIZE];
        } else if (addressBytes.length == IPV6_ADDRESS_SIZE) {
            family = IPV6_FAMILY_CODE;
            xAddress = generateIPV6XAddress(addressBytes, transactionID);
            value = new byte[IPV6_ATTRIBUTE_SIZE];
        } else {
            throw new RuntimeException(
                    "address is not the right size. It was " + addressBytes.length +
                            " bytes. XOR Mapped Address attributes must be either " +
                            IPV4_ADDRESS_SIZE + " or " + IPV6_ADDRESS_SIZE + " bytes.");
        }

        combineXORMappedAddressComponents(value, family, xPort, xAddress);

        return value;
    }

    private static void combineXORMappedAddressComponents(byte[] combined, byte family, short xPort, byte[] xAddress) {
        combined[1] = family;
        combined[2] = unsignByte(xPort >>> 8);
        combined[3] = (byte) xPort;

        System.arraycopy(xAddress,
                0,
                combined,
                4,
                xAddress.length);
    }

    private static short generateXPort(int port) {
        byte[] portBytes = StunMessageUtils.convert((short) port);

        short magicCookieTopBytes = MagicCookie.getTopTwoBytes();
        byte[] magicCookieBytes = StunMessageUtils.convert(magicCookieTopBytes);

        byte[] xORedResult = StunMessageUtils.xOR(portBytes, magicCookieBytes);

        short xPort = unsignShort(xORedResult[0]);
        xPort <<= 8;
        xPort |= unsignShort(xORedResult[1]);

        return xPort;
    }

    private static byte[] generateIPV4XAddress(byte[] address) {
        byte[] magicCookieBytes = MagicCookie.getBytesBigEndian();
        return StunMessageUtils.xOR(magicCookieBytes, address);
    }

    private static byte[] generateIPV6XAddress(byte[] address, byte[] transactionID) {
        byte[] magicCookieBytes = MagicCookie.getBytesBigEndian();
        byte[] magicCookiePlusTransactionID = combineArrays(magicCookieBytes, transactionID);
        return StunMessageUtils.xOR(magicCookiePlusTransactionID, address);
    }

    private static byte[] combineArrays(byte[] first, byte[] second) {
        byte[] destination = new byte[first.length + second.length];
        System.arraycopy(
                first,
                0,
                destination,
                0,
                first.length);

        System.arraycopy(
                second,
                0,
                destination,
                first.length,
                second.length);

        return destination;
    }

    private static byte unsignByte(int number) {
        return (byte) (number & StunMessageUtils.MASK);
    }

    private static short unsignShort(int number) {
        return (short) (number & StunMessageUtils.MASK);
    }

    public static void setOverrideAddress(InetSocketAddress address) {
        overrideAddress = address;
    }
}
