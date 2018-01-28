package com.techgarden.payu.controller;

import com.techgarden.payu.model.Product;
import com.techgarden.payu.service.PayUService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/api/order")
public class OrderController {
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    private PayUService payUService;

    @Autowired
    public OrderController(PayUService payUService) {
        this.payUService = payUService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createOrder(@RequestBody List<Product> products, HttpServletRequest request, HttpServletResponse response) {
        if(products.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            LOG.error("Product list is empty");
            return;
        }
        LOG.info("Creating order for products: {}", products);
        String customerIp = request.getRemoteAddr();
        String redirectionUrl = payUService.processOrder(customerIp, products);
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", redirectionUrl);
    }
}
