package com.hal.stun;

import com.hal.stun.message.StunMessage;
import com.hal.stun.message.StunHeader;
import com.hal.stun.message.MessageClass;
import com.hal.stun.message.attribute.StunAttribute;
import com.hal.stun.message.attribute.AttributeType;

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

  @Test
  public void testErrorAttributeHandleParseError() throws Exception {
    StunApplication application = new StunApplication();
    byte[] rawRequest = ClientTestData.MALFORMED_REQUEST_IPV4;

    byte[] rawResponse = application.handle(rawRequest, null);

    StunMessage parsedResponse = new StunMessage(rawResponse, null);
    StunAttribute errorAttribute = null;
    for (StunAttribute attribute : parsedResponse.getAttributes()) {
      if (attribute.getAttributeType() == AttributeType.ERROR_CODE) {
        errorAttribute = attribute;
      }
    }

    Assert.assertNotNull("error code attribute set in response",
                         errorAttribute);
  }
}
