package com.hal.stun.message;

public class StunParseException extends Exception {

  public StunParseException(String message) {
    super(message);
  }
  
  public StunParseException(String message, Exception exception) {
    super(message, exception);
  }
  
}
