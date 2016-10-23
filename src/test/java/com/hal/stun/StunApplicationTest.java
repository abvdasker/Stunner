package com.hal.stun;

import com.hal.stun.message.StunMessage;
import com.hal.stun.message.StunHeader;
import com.hal.stun.message.MessageClass;
import com.hal.stun.message.StunParseException;

import com.hal.stun.client.data.ClientTestData;
import org.junit.Test;
import org.junit.Assert;

public class StunApplicationTest {

  @Test
  public void testMessageClassHandleParseError() throws Exception {
    StunApplication application = new StunApplication();
    byte[] rawRequest = ClientTestData.MALFORMED_REQUEST_IPV4;

    byte[] rawResponse = application.handle(rawRequest, null);

    StunMessage parsedResponse = new StunMessage(rawResponse, null);
    StunHeader responseHeader = parsedResponse.getHeader();
    Assert.assertEquals("Message class of response header is ERROR",
                        MessageClass.ERROR,
                        responseHeader.getMessageClass());
                        
  }
}
