package com.techgarden.payu.dao;

import com.techgarden.payu.model.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OAuthDao {

    private final static String API_GET_TOKEN = "/pl/standard/user/oauth/authorize";

    private RestTemplate restTemplate;
    private String payuUrl;
    private String clientId;
    private String clientSecret;

    @Autowired
    public OAuthDao(RestTemplate restTemplate,
                    @Value("${payu.url}") String payuUrl,
                    @Value("${payu.client.id}") String clientId,
                    @Value("${payu.client.secret}") String clientSecret) {
        this.restTemplate = restTemplate;
        this.payuUrl = payuUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public OAuthResponse getCredentials() {
        String grantType = "client_credentials";
        String requestBody = String.format("grant_type=%s&client_id=%s&client_secret=%s", grantType, clientId, clientSecret);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForObject(payuUrl + API_GET_TOKEN, entity, OAuthResponse.class);
    }
}
