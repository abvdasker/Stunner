package com.hal.stun.message.attribute.value;

import java.security.MessageDigest;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Mac;

import com.hal.stun.message.StunParseException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

public class MessageIntegrityStunAttributeValue extends StunAttributeValue {

  private static final int BYTE_SIZE = 20;

  private byte[] hmac;
  
  public MessageIntegrityStunAttributeValue(byte[] value) throws StunParseException {
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
    return result == hmac;
  }

  protected boolean isValid() {
    return value.length == BYTE_SIZE;
  }

  protected void parseValueBytes() throws StunParseException {
    hmac = value;
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
