package com.bridge.data.account.controller;

import com.bridge.data.account.service.AccountService;
import com.bridge.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    AccountService accountService;

    @PostMapping("/send-to-crm")
    public ApiResponse<Void> sendToCrm(){
        accountService.sendToCrm();

        return new ApiResponse<>(
                "SUCCESS",
                "Synchronization initialized",
                LocalDateTime.now(),
                null
        );
    }

}
