package com.robovalet.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class TestController {
	
	@RequestMapping("/")
	public String testRoute() {
		return "Hello!!!";
	}

}
