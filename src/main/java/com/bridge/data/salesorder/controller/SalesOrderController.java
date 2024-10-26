package com.bridge.data.salesorder.controller;

import com.bridge.data.salesorder.service.SalesOrderService;
import com.bridge.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/api/sales_orders")
public class SalesOrderController {

    SalesOrderService salesOrderService;

    @PostMapping("/send-to-crm")
    public ApiResponse<Void> sendToCrm(){
        salesOrderService.sendToCrm();

        return new ApiResponse<>(
                "SUCCESS",
                "Synchronization initialized",
                LocalDateTime.now(),
                null
        );
    }
}
