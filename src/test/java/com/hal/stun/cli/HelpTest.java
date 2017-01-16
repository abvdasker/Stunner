package com.hal.stun.cli;

import org.junit.Assert;
import org.junit.Test;

public class HelpTest {

    @Test
    public void testGetHelpText() {
        Assert.assertNotNull(Help.getHelpText());
    }
}
