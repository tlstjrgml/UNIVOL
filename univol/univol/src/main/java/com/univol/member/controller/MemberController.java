package com.univol.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

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
	
	
	/* 로그인페이지로이동 */
	@GetMapping("/logIn")
	public String logIn() {
		return ("users/logIn");
	}
	
	

	/* 로그인하면 메인페이지로 이동 */
	@PostMapping("/logIn")
	public String logInSuccess(@ModelAttribute Member m, HttpSession session) {
		Member loginUser = mService.logIn(m);
		if(loginUser != null) {
			session.setAttribute("loginUser", loginUser);
			return("main/mainPage");
		}else {
			return("users/logIn");
		}
	}

	/* 회원가입페이지로이동 */
	@GetMapping("/signUp")
	public String signUp() {
		return("users/signUp");
	}

	/* 회원가입 성공하면 로그인 페이지로 이동 */
	@PostMapping("/signUp")
	public String signUpSuccess(@ModelAttribute Member m) {
		mService.signUp(m);
		return("redirect:/logIn");
	}
	
	/* 마이페이지 */
	@GetMapping("/myPage")
	public String myPage() {
		return null;
	}
	
	/* 로그아웃 */
	@GetMapping("/logOut")
	public String logOut(SessionStatus status) {
		status.setComplete();
		return("redirect:/");
	}
	

}
