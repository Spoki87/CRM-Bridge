package com.bridge.zoho.auth.model;

import com.bridge.zoho.auth.dto.TokenResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Token {

    private static Token instance;

    private String accessToken;
    private LocalDateTime expiryTime;

    private Token() {}

    public static synchronized Token getInstance() {
        if (instance == null) {
            instance = new Token();
        }
        return instance;
    }

    public void update(TokenResponse tokenResponse){
        this.accessToken = tokenResponse.getAccessToken();
        this.expiryTime = LocalDateTime.now().plusSeconds(tokenResponse.getExpiresIn());
    }

    public String getAccessToken(){
        return this.accessToken;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryTime);
    }
}
