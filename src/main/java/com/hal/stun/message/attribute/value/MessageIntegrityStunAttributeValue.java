package com.hal.stun.message.attribute.value;

import java.security.MessageDigest;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Mac;

import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

public class MessageIntegrityStunAttributeValue extends StunAttributeValue {

  private static final int BYTE_SIZE = 20;

  public MessageIntegrityStunAttributeValue(byte[] value) throws StunAttributeValueParseException {
    super(value);
  }

  public boolean verifyMessageIntegrity(byte[] messageBytes, String username, String realm, String password) {
    byte[] key = computeKey(username, realm, password);
    byte[] result;
    try {
      SecretKeySpec keySpec = new SecretKeySpec(key, "HmacSHA1");
      Mac mac = Mac.getInstance("HmacSHA1");
      mac.init(keySpec);
      result = mac.doFinal(messageBytes);
    } catch (NoSuchAlgorithmException exception) {
      throw new RuntimeException(exception);
    } catch (InvalidKeyException exception) {
      return false;
    }
    return result == value;
  }

  protected boolean isValid() {
    return value.length == BYTE_SIZE;
  }

  protected void parseValueBytes() throws StunAttributeValueParseException {
  }

  private static byte[] computeKey(String username, String realm, String password) {
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException exception) {
      throw new RuntimeException(exception);
    }
    String undigested = username + ":" + realm + ":" + password;
    return digest.digest(undigested.getBytes());
  }
}
