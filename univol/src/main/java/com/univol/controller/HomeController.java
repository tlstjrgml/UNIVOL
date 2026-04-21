package com.univol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String index() {
		return "mainPage";
	}
	
	@GetMapping("/logIn")
	public String logIn() {
		return "logIn";
	}

}
