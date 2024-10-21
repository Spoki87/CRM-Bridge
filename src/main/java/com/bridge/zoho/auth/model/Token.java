package com.bridge.zoho.auth.model;

import com.bridge.zoho.auth.dto.TokenResponse;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@NoArgsConstructor
public class Token {


    private String accessToken;
    private LocalDateTime expiryTime = LocalDateTime.now();

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
