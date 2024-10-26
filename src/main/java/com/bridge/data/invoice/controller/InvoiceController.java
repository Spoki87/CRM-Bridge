package com.bridge.data.invoice.controller;

import com.bridge.data.invoice.service.InvoiceService;
import com.bridge.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    InvoiceService invoiceService;

    @PostMapping("/send-to-crm")
    public ApiResponse<Void> sendToCrm(){
        invoiceService.sendToCrm();

        return new ApiResponse<>(
                "SUCCESS",
                "Synchronization initialized",
                LocalDateTime.now(),
                null
        );
    }
}
