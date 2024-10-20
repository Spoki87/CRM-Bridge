package com.bridge.zoho.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class CrmUrlBuilder {

    @Value("${crm.base-url}")
    private String baseUrl;

    @Value("${crm.server}")
    private String server;

    @Value("${crm.api-version}")
    private String apiVersion;

    public String buildRequestUrl(String module){
        return String.format("%s%s/crm/%s/%s",baseUrl,server,apiVersion,module);
    }

    public String buildTokenUrl(){
        return String.format("%s%s/oauth/v2/token",baseUrl,server);
    }

}
