package com.techgarden.payu.dao;

import com.techgarden.payu.model.OAuthResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(MockitoJUnitRunner.class)
public class OAuthDaoTest {

    private RestTemplate restTemplate;
    private String payuUrl = "http://payu.url";
    private String clientId = "1234";
    private String clientSecret = "top_secret";

    private MockRestServiceServer server;

    private OAuthDao oAuthDao;

    @Before
    public void setup() {
        restTemplate = new RestTemplate();
        server = MockRestServiceServer.createServer(restTemplate);
        oAuthDao = new OAuthDao(restTemplate, payuUrl, clientId, clientSecret);
    }

    @Test
    public void testGetCredentials() {
        String requestBody = "grant_type=client_credentials&client_id=1234&client_secret=top_secret";
        String response = "{" +
                "  \"access_token\": \"cb5fabbb-f0a3-4f57-903e-f2df0315d039\"," +
                "  \"token_type\": \"bearer\"," +
                "  \"expires_in\": 43199," +
                "  \"grant_type\": \"client_credentials\"" +
                "}";

        server.expect(requestTo(payuUrl + "/pl/standard/user/oauth/authorize"))
                .andExpect(content().contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(content().string(requestBody))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        OAuthResponse actual = oAuthDao.getCredentials();
        OAuthResponse expected = new OAuthResponse("cb5fabbb-f0a3-4f57-903e-f2df0315d039", "bearer", 43199, "client_credentials");

        assertEquals(expected, actual);
    }
}
