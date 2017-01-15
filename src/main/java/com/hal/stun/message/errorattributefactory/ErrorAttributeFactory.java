package com.hal.stun.message.errorattributefactory;

import com.hal.stun.message.attribute.StunAttribute;

import java.util.List;

public interface ErrorAttributeFactory {
    public List<StunAttribute> build();
}
