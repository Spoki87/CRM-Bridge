package com.bridge.data.quote.controller;

import com.bridge.data.quote.service.QuoteService;
import com.bridge.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    QuoteService quoteService;

    @PostMapping("/send-to-crm")
    public ApiResponse<Void> sendToCrm(){
        quoteService.sendToCrm();

        return new ApiResponse<>(
                "SUCCESS",
                "Synchronization initialized",
                LocalDateTime.now(),
                null
        );
    }
}
