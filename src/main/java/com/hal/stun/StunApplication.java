package com.hal.stun;

import com.hal.stun.message.StunMessage;
import com.hal.stun.message.StunRequestMessage;
import com.hal.stun.message.StunResponseMessage;
import com.hal.stun.message.StunSuccessResponseMessage;
import com.hal.stun.message.StunErrorResponseMessage;
import com.hal.stun.message.StunHeader;
import com.hal.stun.message.MessageClass;
import com.hal.stun.message.errorattributefactory.ErrorAttributeFactory;
import com.hal.stun.message.errorattributefactory.UnrecognizedAttributeErrorAttributeFactory;
import com.hal.stun.message.errorattributefactory.ServerErrorAttributeFactory;
import com.hal.stun.message.errorattributefactory.InvalidRequestErrorAttributeFactory;

import com.hal.stun.message.StunParseException;
import com.hal.stun.message.attribute.UnrecognizedAttributeTypeException;

import java.net.InetSocketAddress;
import java.util.logging.Logger;

class StunApplication {

  private static final Logger log = Logger.getLogger(StunApplication.class.getName());
  // handler also needs some info about the connection like
  // source IP, request received time, etc.
  public byte[] handle(byte[] rawRequest, InetSocketAddress address) {
    ErrorAttributeFactory errorAttributeFactory = null;
    StunResponseMessage response = null;
    try {
      StunRequestMessage request = new StunRequestMessage(rawRequest, address);
      log.info("request:\n" + request);
      response = buildResponse(request);
    } catch (UnrecognizedAttributeTypeException  exception) {
      errorAttributeFactory = new UnrecognizedAttributeErrorAttributeFactory(exception);
    } catch (StunParseException exception) {
      errorAttributeFactory = new InvalidRequestErrorAttributeFactory(exception);
    } catch (Exception exception) {
      errorAttributeFactory = new ServerErrorAttributeFactory(exception);
    }
    if (response == null) {
      response = new StunErrorResponseMessage(rawRequest, errorAttributeFactory);
    }
    log.info("response:\n" + response);
    return response.getBytes();
  }

  private static StunResponseMessage buildResponse(StunRequestMessage request) throws UnsupportedStunClassException, StunParseException {
    StunHeader header = request.getHeader();
    MessageClass requestMessageClass = header.getMessageClass();
    StunResponseMessage response;
    switch(requestMessageClass) {
    case REQUEST:
      response = new StunSuccessResponseMessage(request);
      break;
    case INDICATION:
    case SUCCESS:
    case ERROR:
    default:
      throw new UnsupportedStunClassException(requestMessageClass);
    }

    return response;
  }

  public static class UnsupportedStunClassException extends Exception {

    public UnsupportedStunClassException(MessageClass messageClass) {
      super("stun message type " + messageClass.name() + " is not supported by the STUN server");
    }

  }

}
