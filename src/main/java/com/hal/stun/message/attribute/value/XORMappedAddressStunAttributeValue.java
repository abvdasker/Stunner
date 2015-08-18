package com.hal.stun.message.attribute.value;

import com.hal.stun.message.StunMessageUtils;
import com.hal.stun.message.StunParseException;

import java.util.Arrays;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class XORMappedAddressStunAttributeValue extends MappedAddressStunAttributeValue {
  public XORMappedAddressStunAttributeValue(String valueHex) throws StunParseException {
    super(valueHex);
  }
}