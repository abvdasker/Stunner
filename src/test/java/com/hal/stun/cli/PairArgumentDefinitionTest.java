package com.hal.stun.cli;

import org.junit.Test;
import org.junit.Assert;

public class PairArgumentDefinitionTest {
    @Test
    public void testGetDefaultArgument() {
        ArgumentDefinition<Integer> definition
                = new PairArgumentDefinition<>(Integer.class,
                "--number",
                "-n",
                123,
                "test number");
        Argument defaultArgument = definition.getDefaultArgument(null);
        Assert.assertEquals("returns the default integer argument",
                123,
                defaultArgument.getInt().intValue());
    }

    @Test
    public void testParse() throws ArgumentParseException {
        ArgumentDefinition<Integer> definition
                = new PairArgumentDefinition<>(Integer.class,
                "--number",
                "-n",
                123,
                "test number");
        Argument argument = definition.parse("456");
        Assert.assertEquals("returns the default integer argument",
                456,
                argument.getInt().intValue());
    }

    @Test(expected = ArgumentParseException.class)
    public void testParseInvalidString() throws ArgumentParseException {
        ArgumentDefinition<Integer> definition
                = new PairArgumentDefinition<>(Integer.class,
                "--number",
                "-n",
                123,
                "test number");
        definition.parse("hello");
    }

    @Test
    public void testGetShortKey() throws ArgumentParseException {
        ArgumentDefinition<Integer> definition
                = new PairArgumentDefinition<>(Integer.class,
                "--number",
                "-n",
                123,
                "test number");

        Assert.assertEquals("-n", definition.getShortKey());
    }

    @Test(expected = ArgumentParseRuntimeException.class)
    public void testParseInvalidConstructorException() throws ArgumentParseException {
        ArgumentDefinition<Object> definition
                = new PairArgumentDefinition<>(Object.class,
                "--blah",
                "-b",
                123,
                "test invalid class");

        definition.parse("77");
    }

    @Test(expected = ArgumentParseException.class)
    public void testParseMissingString() throws ArgumentParseException {
        ArgumentDefinition<Object> definition
                = new PairArgumentDefinition<>(Object.class,
                "--blah",
                "-b",
                123,
                "test invalid class");

            definition.parse(null);
    }
}
