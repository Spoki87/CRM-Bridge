package com.bridge.zoho.auth.service;

import com.bridge.zoho.auth.dto.TokenResponse;
import com.bridge.zoho.auth.model.Token;
import com.bridge.zoho.utils.CrmUrlBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TokenService {

    private final Token token;
    private final CrmUrlBuilder crmUrlBuilder;
    private final RestTemplate restTemplate;

    public TokenService(Token token,CrmUrlBuilder crmUrlBuilder, RestTemplate restTemplate) {
        this.token = token;
        this.crmUrlBuilder = crmUrlBuilder;
        this.restTemplate = restTemplate;
    }

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

        String requestUrl = UriComponentsBuilder.fromHttpUrl(refreshTokenUrl)
                .queryParam("refresh_token", refreshToken)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("grant_type", "refresh_token")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange(requestUrl, HttpMethod.POST, request, TokenResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            updateToken(response.getBody());
        } else {
            throw new RuntimeException("Error is refreshing token");
        }
    }

}
