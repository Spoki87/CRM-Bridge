package com.bridge.data.deal.controller;

import com.bridge.data.deal.service.DealService;
import com.bridge.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/api/deals")
public class DealController {

    DealService dealService;

    @PostMapping("/send-to-crm")
    public ApiResponse<Void> sendToCrm(){
        dealService.sendToCrm();

        return new ApiResponse<>(
                "SUCCESS",
                "Synchronization initialized",
                LocalDateTime.now(),
                null
        );
    }
}
