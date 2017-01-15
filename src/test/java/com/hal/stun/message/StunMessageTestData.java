package com.hal.stun.message;

import java.util.Arrays;

public class StunMessageTestData {
    public static final int[] REAL_STUN_MESSAGE = {
            // header
            0, // message type
            1, // message type
            0, // message length (not including header)
            8, // message length (not including header)
            33, // magic cookie
            18, // magic cookie
            164, // magic cookie
            66, // magic cookie
            175, // transaction id
            69, // transaction id
            81, // transaction id
            45, // transaction id
            10, // transaction id
            63, // transaction id
            90, // transaction id
            49, // transaction id
            146, // transaction id
            132, // transaction id
            11, // transaction id
            93, // transaction id

            // message body
            128, // type (Fingerprint)
            40, // type (Fingerprint)
            0, // length (of attribute value)
            4, // length (of attribute value)
            195, // fingerprint value
            91, // fingerprint value
            242, // fingerprint value
            149 // fingerprint value
    };

    public static final String REAL_STUN_ADDRESS = "172.27.6.183";

    public static byte[] getRealTransactionID() {
        int transactionIDIndex = 8;
        int transactionIDLength = 12;
        byte[] transactionID = Arrays.copyOfRange(getRealStunMessageBytes(), transactionIDIndex, transactionIDIndex + transactionIDLength);
        return transactionID;
    }

    public static byte[] getRealStunMessageBytes() {
        return convertToByteArray(REAL_STUN_MESSAGE);
    }

    private static byte[] convertToByteArray(int[] array) {
        byte[] converted = new byte[array.length];
        for (int i = 0; i < converted.length; i++) {
            converted[i] = (byte) (array[i] & 0xFF);
        }

        return converted;
    }
}
