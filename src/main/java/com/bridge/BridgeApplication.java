package com.bridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BridgeApplication.class, args);
	}
}
