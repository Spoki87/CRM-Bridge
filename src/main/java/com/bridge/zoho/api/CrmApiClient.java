package com.bridge.zoho.api;

import com.bridge.zoho.model.response.CrmResponse;
import com.bridge.zoho.auth.model.Token;
import com.bridge.zoho.model.request.CrmRequest;
import com.bridge.zoho.utils.CrmUrlBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class CrmApiClient {

    private final RestTemplate restTemplate;
    CrmUrlBuilder crmUrlBuilder;
    private final Token token;


    public <T> void sendDataToCrm(CrmRequest<T> requestData, String module) {

        String requestUrl = crmUrlBuilder.buildRequestUrl(module);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CrmRequest<T>> request = new HttpEntity<>(requestData, headers);

        ResponseEntity<CrmResponse> response = restTemplate.exchange(requestUrl, HttpMethod.POST, request, CrmResponse.class);

    }
}
