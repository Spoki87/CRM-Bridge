package com.bridge.data.lead.controller;

import com.bridge.data.lead.service.LeadService;
import com.bridge.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/api/leads")
public class LeadController {

    LeadService leadService;

    @PostMapping("/send-to-crm")
    public ApiResponse<Void> sendToCrm(){
         leadService.sendToCrm();

        return new ApiResponse<>(
                "SUCCESS",
                "Synchronization initialized",
                LocalDateTime.now(),
                null
        );
    }
}
