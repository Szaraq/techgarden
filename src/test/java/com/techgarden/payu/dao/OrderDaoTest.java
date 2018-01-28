package com.techgarden.payu.dao;

import com.techgarden.payu.model.OrderCreateResponse;
import com.techgarden.payu.model.OrderCreateStatus;
import com.techgarden.payu.model.Product;
import org.eclipse.collections.impl.factory.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(MockitoJUnitRunner.class)
public class OrderDaoTest {

    private RestTemplate restTemplate;
    private String payuUrl = "http://payu.url";
    private String shopUrl = "http://my.shop.pl";
    private String merchantPosId = "145227";
    private String merchantDescription = "RTV market";
    private String currencyCode = "PLN";

    private MockRestServiceServer server;

    private OrderDao orderDao;

    @Before
    public void setup() {
        restTemplate = new RestTemplate();
        server = MockRestServiceServer.createServer(restTemplate);
        orderDao = new OrderDao(restTemplate, payuUrl, shopUrl, merchantPosId, merchantDescription, currencyCode);
    }

    @Test
    public void testCreateOrder() {
        String requestBody = "{" +
                "\"notifyUrl\":\"http://my.shop.pl\"," +
                "\"customerIp\":\"127.0.0.1\"," +
                "\"merchantPosId\":\"145227\"," +
                "\"description\":\"RTV market\"," +
                "\"currencyCode\":\"PLN\"," +
                "\"totalAmount\":\"27000\"," +
                "\"products\":[" +
                "{" +
                "\"name\":\"Wireless mouse\"," +
                "\"unitPrice\":\"15000\"," +
                "\"quantity\":\"1\"" +
                "}," +
                "{" +
                "\"name\":\"HDMI cable\"," +
                "\"unitPrice\":\"6000\"," +
                "\"quantity\":\"2\"" +
                "}" +
                "]" +
                "}";
        String token = "123token456";
        String response = "{\n" +
                "  \"orderId\": \"MVBHZJGGCQ180127GUEST000P01\",\n" +
                "  \"status\": {\n" +
                "    \"statusCode\": \"SUCCESS\"\n" +
                "  },\n" +
                "  \"redirectUri\": \"http://redirect.to\"\n" +
                "}";
        List<Product> productList = Lists.mutable.of(
                new Product("Wireless mouse", "15000", "1"),
                new Product("HDMI cable", "6000", "2")
        );

        server.expect(requestTo(payuUrl + "/api/v2_1/orders"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(requestBody))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Authorization", "Bearer " + token))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        OrderCreateResponse expected = new OrderCreateResponse("MVBHZJGGCQ180127GUEST000P01", new OrderCreateStatus("SUCCESS"), "http://redirect.to");
        OrderCreateResponse actual = orderDao.createOrder("127.0.0.1", token, productList);

        assertEquals(expected, actual);
    }
}
