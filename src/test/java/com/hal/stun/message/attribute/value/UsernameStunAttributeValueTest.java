package com.hal.stun.message.attribute.value;

import com.hal.stun.client.data.ClientTestData;
import com.hal.stun.message.attribute.value.UsernameStunAttributeValue;

import org.junit.Test;
import org.junit.Assert;

public class UsernameStunAttributeValueTest { // - 44

    @Test
    public void testParseValue() throws Exception {
        byte[] usernameBytes = ClientTestData.getUsernameValueV4();

        UsernameStunAttributeValue value = new UsernameStunAttributeValue(usernameBytes);

        String expectedUsername = "evtj:h6vY";
        String parsedUsername = value.getUsername();

        Assert.assertEquals("attribute correctly parses username from byte array",
                expectedUsername,
                parsedUsername);
    }

}
