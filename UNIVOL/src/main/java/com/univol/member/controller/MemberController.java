package com.univol.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	// 맥zzzz//
	
	
	/* 메인 페이지 */
	@GetMapping("/")
	public String main() {
		return "main/mainPage";
	}

	/* 로그인 페이지로 이동 */
	@GetMapping("/logIn")
	public String logIn() {
		return "users/logIn";
	}

	/* 로그인 처리 */
	@PostMapping("/logIn")
	public String logInSuccess(@ModelAttribute Member m, HttpSession session, Model model) {
		Member loginUser = mService.logIn(m);
		if (loginUser != null) {
			session.setAttribute("loginUser", loginUser);
			return "redirect:/";
		} else {
			model.addAttribute("error", "아이디 또는 비밀번호가 틀렸습니다.");
			return "users/logIn";
		}
	}

	
	/*로그아웃*/
	  
	@GetMapping("/logout") public String logout(SessionStatus status) {
		status.setComplete(); 
		return "redirect:/";  
	}
	  
	/*회원가입 페이지로 이동*/
	@GetMapping("/signUp") public String signUp() { 
		return "users/signUp"; 
	}
	  
	/* 회원가입 처리 */
	@PostMapping("/signUp") public String signUpSuccess(@ModelAttribute Member m){
		mService.signUp(m); return "redirect:/logIn"; 
	}
	 
	/* 마이페이지 */
	
	/* 관리자페이지 */
	
	/* 게시글 상세조회 */
	
	/* 봉사게시판 */
	@GetMapping("/volunteer")
	public String volunteer() {
		return "volunteer/volunteer";
	}
	/* 후기게시판 */
	@GetMapping("/review")
	public String review() {
		return "review/review";
	}
}