package com.techgarden.payu.dao;

import com.techgarden.payu.model.OrderCreateRequest;
import com.techgarden.payu.model.OrderCreateResponse;
import com.techgarden.payu.model.Product;
import org.eclipse.collections.impl.factory.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderDao {
    private final static String API_CREATE_ORDER = "/api/v2_1/orders";

    private RestTemplate restTemplate;
    private String payuUrl;
    private String shopUrl;
    private String merchantPosId;
    private String merchantDescription;
    private String currencyCode;

    @Autowired
    public OrderDao(RestTemplate restTemplate,
                    @Value("${payu.url}") String payuUrl,
                    @Value("${application.shop.url}") String shopUrl,
                    @Value("${payu.merchant.posid}") String merchantPosId,
                    @Value("${payu.merchant.description}") String merchantDescription,
                    @Value("${payu.currency.code}") String currencyCode) {
        this.restTemplate = restTemplate;
        this.payuUrl = payuUrl;
        this.shopUrl = shopUrl;
        this.merchantPosId = merchantPosId;
        this.merchantDescription = merchantDescription;
        this.currencyCode = currencyCode;
    }

    public OrderCreateResponse createOrder(String customerIp, String token, List<Product> products) {
        OrderCreateRequest requestBody = new OrderCreateRequest(shopUrl, customerIp, merchantPosId, merchantDescription, currencyCode, products);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.put("Authorization", Lists.mutable.of("Bearer " + token));
        HttpEntity<OrderCreateRequest> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForObject(payuUrl + API_CREATE_ORDER, entity, OrderCreateResponse.class);
    }
}
