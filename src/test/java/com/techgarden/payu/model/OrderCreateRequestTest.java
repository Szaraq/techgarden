package com.techgarden.payu.model;

import org.eclipse.collections.impl.factory.Lists;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderCreateRequestTest {

    @Test
    public void testRecalculateInConstructor() {
        OrderCreateRequest request = new OrderCreateRequest("notifyUrl", "ip", "posId", "desc", "PLN", Lists.mutable.of(
                new Product("Wireless mouse", "15000", "1"),
                new Product("HDMI Cable", "20000", "2")
        ));
        assertEquals("55000", request.getTotalAmount());
    }

    @Test
    public void testRecalculateInSetProducts() {
        OrderCreateRequest request = new OrderCreateRequest("notifyUrl", "ip", "posId", "desc", "PLN", Lists.mutable.empty());
        request.setProducts(Lists.mutable.of(
                new Product("Wireless mouse", "15000", "1"),
                new Product("HDMI Cable", "20000", "2")
        ));
        assertEquals("55000", request.getTotalAmount());
    }
}
