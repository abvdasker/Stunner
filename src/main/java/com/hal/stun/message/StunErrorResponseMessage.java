package com.hal.stun.message;

import java.util.List;
import java.util.ArrayList;

import com.hal.stun.message.attribute.StunAttribute;
import com.hal.stun.message.errorattributefactory.ErrorAttributeFactory;

public class StunErrorResponseMessage extends StunResponseMessage {

    public StunErrorResponseMessage(byte[] rawRequest, ErrorAttributeFactory errorAttributeFactory) {
        attributes = buildResponseAttributes(errorAttributeFactory);
        int messageLength = getAttributeListByteLength(attributes);
        byte[] transactionID;
        try {
            StunHeader requestHeader = new StunHeader(getHeaderBytes(rawRequest));
            transactionID = requestHeader.getTransactionID();
        } catch (StunParseException headerParseException) {
            transactionID = new byte[0];
        }
        header = new StunHeader(MessageClass.ERROR,
                StunHeader.BINDING_METHOD,
                messageLength,
                transactionID);
        updateFingerprint(attributes);
    }

    private static List<StunAttribute> buildResponseAttributes(ErrorAttributeFactory errorAttributeFactory) {
        List<StunAttribute> attributes = new ArrayList<>();
        attributes.add(buildSoftwareAttribute());
        attributes.addAll(errorAttributeFactory.build());
        attributes.add(buildFingerprintAttribute());
        return attributes;
    }

}
