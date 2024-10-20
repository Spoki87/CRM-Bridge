package com.bridge.zoho.auth.service;

import com.bridge.zoho.auth.dto.TokenResponse;
import com.bridge.zoho.auth.model.Token;
import com.bridge.zoho.utils.CrmUrlBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class TokenService {

    private Token token = Token.getInstance();
    private CrmUrlBuilder crmUrlBuilder;
    private RestTemplate restTemplate;

    @Value("${crm.client-id}")
    private String clientId;

    @Value("${crm.client-secret}")
    private String clientSecret;

    @Value("${crm.refresh-token}")
    private String refreshToken;



    public String getAccessToken() {
        if (token.isExpired()) {
            refreshAccessToken();
        }
        return token.getAccessToken();
    }

    private void updateToken(TokenResponse tokenResponse){
        token.update(tokenResponse);
    }


    private void refreshAccessToken() {

        String refreshTokenUrl = crmUrlBuilder.buildTokenUrl();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("refresh_token", refreshToken);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("grant_type", "refresh_token");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange(refreshTokenUrl, HttpMethod.POST, request, TokenResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            updateToken(response.getBody());
        } else {
            throw new RuntimeException("Error is refreshing token");
        }
    }

}
