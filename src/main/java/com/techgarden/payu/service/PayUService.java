package com.techgarden.payu.service;

import com.techgarden.payu.dao.OAuthDao;
import com.techgarden.payu.dao.OrderDao;
import com.techgarden.payu.model.OrderCreateResponse;
import com.techgarden.payu.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayUService {

    private OAuthDao oAuthDao;
    private OrderDao orderDao;

    @Autowired
    public PayUService(OAuthDao oAuthDao, OrderDao orderDao) {
        this.oAuthDao = oAuthDao;
        this.orderDao = orderDao;
    }

    public String processOrder(String customerIp, List<Product> products) {
        String token = oAuthDao.getCredentials().getAccessToken();
        OrderCreateResponse order = orderDao.createOrder(customerIp, token, products);
        return order.getRedirectUri();
    }
}
