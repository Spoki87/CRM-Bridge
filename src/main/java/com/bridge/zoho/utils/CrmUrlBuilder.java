package com.bridge.zoho.utils;

import com.bridge.zoho.enums.Module;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class CrmUrlBuilder {

    @Value("${crm.base-url}")
    private String baseUrl;

    @Value(("${crm.account-url}"))
    private String accountUrl;

    @Value("${crm.api-version}")
    private String apiVersion;

    public String buildRequestUrl(Module module){
        return String.format("%s/crm/%s/%s",baseUrl,apiVersion,module.getModuleName());
    }

    public String buildTokenUrl(){
        return String.format("%s/oauth/v2/token",accountUrl);
    }

}
