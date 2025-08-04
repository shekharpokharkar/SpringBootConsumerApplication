package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController2 {

	
	@GetMapping("/")
	public String showHello() {
		return "Hello World from shekhar pokharkar";
	}
}
