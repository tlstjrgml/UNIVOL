package com.univol.member.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.univol.member.model.service.MemberService;
import com.univol.member.model.vo.Member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@SessionAttributes("loginUser")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService mService;
	

	
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
	@GetMapping("/myPage")
	public ModelAndView myPage(HttpSession session, ModelAndView mv) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		if(loginUser != null) {
			String id = loginUser.getUserId();
			ArrayList<HashMap<String, Object>> applyList = mService.getApplyList(id);
			
			mv.addObject("applyList", applyList);
			mv.setViewName("users/myPage");
		}
		return mv;

	}
	
	/*개인정보수정 페이지 이동*/
	@GetMapping("/edit")
	public String edit() {
		return "users/edit";
	}
	
	/*개인정보수정 (이메일 미포함 , else문쪽 throw 미포함상태)*/
	@PostMapping("/edit")
	public String updateMember(@ModelAttribute Member m, Model model) {

	    int result = mService.updateMember(m);

	    if(result > 0) {
	    	model.addAttribute("loginUser", mService.logIn(m));
	        return "redirect:/myPage";
	    } else {
	        return "users/edit";
	    }
	}
	
	
	
	/*개인정보수정 에서 비밀번호 수정*/
	
	
	
	/* 관리자페이지 */
	@GetMapping("/adminPage")
	public String adminPage(@ModelAttribute Member m, Model model, HttpSession session) {
		ArrayList<Member> mlist = mService.selectAll();
		model.addAttribute("mlist", mlist);
		Member loginUser = (Member)session.getAttribute("loginUser");
		if(loginUser != null &&  loginUser.getIsAdmin().equals("Y")) {     
			return "users/adminPage";
		}else {
			return "redirect:/common/errorPage";
		}
	}
	
	
	/* 게시글 상세조회 */
	

	/* 후기게시판 */
	@GetMapping("/review")
	public String review() {
		return "review/review";
	}
}