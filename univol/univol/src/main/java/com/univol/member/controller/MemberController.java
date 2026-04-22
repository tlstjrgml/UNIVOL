package com.univol.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.univol.member.model.service.MemberService;
import com.univol.member.model.vo.Member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@SessionAttributes("loginUser")
@RequiredArgsConstructor

public class MemberController {
	private final MemberService mService;
	@GetMapping("/")
	public String main() {
		return ("main/mainPage");
	}
	
	
	
	@GetMapping("/logIn")
	public String logIn() {
		return ("users/logIn");
	}
	
	@PostMapping("/logIn")
	public String logInSuccess(@ModelAttribute Member m, HttpSession session) {
		Member loginUser = mService.logIn(m);
		return("main/mainPage");
	}
	
	@GetMapping("/signUp")
	public String signUp() {
		return("users/signUp");
	}
	

}
