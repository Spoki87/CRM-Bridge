package com.bridge.data.lead.controller;

import com.bridge.data.lead.service.LeadService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/leads")
public class LeadController {

    LeadService leadService;

    @PostMapping("/send-to-crm")
    public void sendToCrm(){
        leadService.sendToCrm();
    }
}
