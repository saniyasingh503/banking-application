package com.nagarro.bench.assignment.customerService.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RefreshScope
public class ConfigController {
	
	@Value("${greeting.message}")
	private String message;
	
	@GetMapping("/greeting")
	public String getGreetingMessage() {
		return "Message : " + message;
	}

}
