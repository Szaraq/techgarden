package com.techgarden.payu.service;

import com.techgarden.payu.dao.OAuthDao;
import com.techgarden.payu.dao.OrderDao;
import com.techgarden.payu.model.OAuthResponse;
import com.techgarden.payu.model.OrderCreateResponse;
import com.techgarden.payu.model.OrderCreateStatus;
import com.techgarden.payu.model.Product;
import org.eclipse.collections.impl.factory.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PayUServiceTest {

    @Mock
    private OAuthDao oAuthDao;
    @Mock
    private OrderDao orderDao;

    private PayUService payUService;

    @Before
    public void setup() {
        payUService = new PayUService(oAuthDao, orderDao);
    }

    @Test
    public void testProcessOrder() {
        String token = "123token456";
        OAuthResponse authResponse = new OAuthResponse(token, "bearer", 43199, "client_credentials");
        String redirectionUrl = "http://redirect.to";
        OrderCreateResponse orderResponse = new OrderCreateResponse("orderId123", new OrderCreateStatus("SUCCESS"), redirectionUrl);
        String customerIp = "192.168.1.0";
        List<Product> products = Lists.mutable.of(
                new Product("Wireless mouse", "15000", "1"),
                new Product("HDMI cable", "6000", "2")
        );

        when(oAuthDao.getCredentials()).thenReturn(authResponse);
        when(orderDao.createOrder(customerIp, token, products)).thenReturn(orderResponse);

        String actual = payUService.processOrder(customerIp, products);

        assertEquals(redirectionUrl, actual);
    }
}
