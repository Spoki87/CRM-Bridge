package com.bridge.data.contact.controller;

import com.bridge.data.contact.service.ContactService;
import com.bridge.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    ContactService contactService;

    @PostMapping("/send-to-crm")
    public ApiResponse<Void> sendToCrm(){
        contactService.sendToCrm();

        return new ApiResponse<>(
                "SUCCESS",
                "Synchronization initialized",
                LocalDateTime.now(),
                null
        );
    }
}
