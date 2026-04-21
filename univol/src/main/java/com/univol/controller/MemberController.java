package com.univol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
@Controller
public class MemberController {

	@PostMapping("/logIn")
	public String logIn(@RequestParam("userId")String userId , 
						@RequestParam("userPw")String userPw,
						HttpSession session,
						Model model) {
		return "redirect:/";
	}
}
