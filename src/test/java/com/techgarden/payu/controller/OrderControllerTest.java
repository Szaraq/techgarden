package com.techgarden.payu.controller;

import com.techgarden.payu.model.Product;
import com.techgarden.payu.service.PayUService;
import org.eclipse.collections.impl.factory.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

    @Mock
    private PayUService payUService;

    private OrderController orderController;

    private HttpServletRequest request;
    private HttpServletResponse response;

    @Before
    public void setup() {
        orderController = new OrderController(payUService);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    public void testCreateOrder() {
        List<Product> products = Lists.mutable.of(
                new Product("Wireless mouse", "15000", "1"),
                new Product("HDMI cable", "6000", "2")
        );
        String customerIp = "192.168.1.0";
        String redirectionUrl = "http://redirect.to";

        when(request.getRemoteAddr()).thenReturn(customerIp);
        when(payUService.processOrder(customerIp, products)).thenReturn(redirectionUrl);
        orderController.createOrder(products, request, response);

        verify(response, times(1)).setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        verify(response, times(1)).setHeader("Location", redirectionUrl);
    }

    @Test
    public void testCreateOrderNoProducts() {
        orderController.createOrder(Lists.mutable.empty(), request, response);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
