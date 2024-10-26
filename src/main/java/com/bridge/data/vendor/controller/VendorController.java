package com.bridge.data.vendor.controller;

import com.bridge.data.vendor.service.VendorService;
import com.bridge.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    VendorService vendorService;

    @PostMapping("/send-to-crm")
    public ApiResponse<Void> sendToCrm(){
        vendorService.sendToCrm();

        return new ApiResponse<>(
                "SUCCESS",
                "Synchronization initialized",
                LocalDateTime.now(),
                null
        );
    }
}
