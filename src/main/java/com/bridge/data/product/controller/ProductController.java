package com.bridge.data.product.controller;

import com.bridge.data.product.service.ProductService;
import com.bridge.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    ProductService productService;

    @PostMapping("/send-to-crm")
    public ApiResponse<Void> sendToCrm(){
        productService.sendToCrm();

        return new ApiResponse<>(
                "SUCCESS",
                "Synchronization initialized",
                LocalDateTime.now(),
                null
        );
    }
}
