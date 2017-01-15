package com.hal.stun.message;

import com.hal.stun.config.StunConfiguration;
import com.hal.stun.config.StunProperties;
import com.hal.stun.message.attribute.StunAttribute;
import com.hal.stun.message.attribute.AttributeType;

import com.hal.stun.message.attribute.value.FingerprintStunAttributeValue;
import com.hal.stun.message.attribute.value.SoftwareStunAttributeValue;

import java.util.List;
import java.util.ArrayList;

public abstract class StunResponseMessage extends StunMessage {

    private static StunProperties configuration = StunConfiguration.getConfig();

    public byte[] getBytes() {
        List<byte[]> messageByteArray = new ArrayList<>();
        messageByteArray.add(header.getBytes());
        for (StunAttribute attribute : attributes) {
            messageByteArray.add(attribute.getBytes());
        }
        return StunMessageUtils.joinByteArrays(messageByteArray);
    }

    // avoid this inefficiency by simply adding 4 to the attribute's length field
    protected static int getAttributeListByteLength(List<StunAttribute> responseAttributes) {
        int responseBodyByteLength = 0;
        for (StunAttribute attribute : responseAttributes) {
            responseBodyByteLength += attribute.getWholeLength();
        }

        return responseBodyByteLength;
    }

    protected void updateFingerprint(List<StunAttribute> responseAttributes) {
        for (StunAttribute attribute : responseAttributes) {
            if (attribute.getAttributeType() == AttributeType.FINGERPRINT) {
                FingerprintStunAttributeValue value = (FingerprintStunAttributeValue) attribute.getValue();
                value.update(getBytesNoFingerprint());
                break;
            }
        }
    }

    protected static StunAttribute buildSoftwareAttribute() {
        try {
            SoftwareStunAttributeValue softwareValue
                    = new SoftwareStunAttributeValue(configuration.getSoftwareName());
            return new StunAttribute(AttributeType.SOFTWARE, softwareValue);
        } catch (StunParseException exception) {
            throw new RuntimeException(exception);
        }
    }

    protected static StunAttribute buildFingerprintAttribute() {
        try {
            return new StunAttribute(AttributeType.FINGERPRINT,
                    FingerprintStunAttributeValue.VALUE_SIZE_BYTES,
                    new byte[FingerprintStunAttributeValue.VALUE_SIZE_BYTES]);
        } catch (StunParseException exception) {
            throw new RuntimeException(exception);
        }
    }

    private byte[] getBytesNoFingerprint() {
        List<byte[]> messageByteArray = new ArrayList<>();
        messageByteArray.add(header.getBytes());
        for (StunAttribute attribute : attributes) {
            if (attribute.getAttributeType() != AttributeType.FINGERPRINT) {
                messageByteArray.add(attribute.getBytes());
            }
        }
        return StunMessageUtils.joinByteArrays(messageByteArray);
    }
}
