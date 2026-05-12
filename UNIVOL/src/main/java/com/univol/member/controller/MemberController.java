package com.univol.member.controller;

import java.util.ArrayList;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.univol.common.PageInfo;
import com.univol.common.Pagination;
import com.univol.member.service.MemberService;
import com.univol.member.vo.Member;
import com.univol.post.service.PostService;
import com.univol.post.service.ReplyService;
import com.univol.post.vo.Post;
import com.univol.post.vo.Reply;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@SessionAttributes("loginUser")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService mService;
	private final PostService pService;
	private final ReplyService rService;
	private final BCryptPasswordEncoder passwordEncoder;
	
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
	        model.addAttribute("loginUser", loginUser);
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
	public ModelAndView myPage(
	        HttpSession session, ModelAndView mv,
	        @RequestParam(value = "applyPage", defaultValue = "1") int applyPage,
	        @RequestParam(value = "myPostPage", defaultValue = "1") int myPostPage) {

	    Member loginUser = (Member) session.getAttribute("loginUser");
	    if (loginUser != null) {
	        String id = loginUser.getUserId();
	        setApplyList(mv, id, applyPage);
	        setMyPostList(mv, id, myPostPage);
	        mv.setViewName("users/myPage");
	    }
	    return mv;
	}

	public void setApplyList(ModelAndView mv, String id, int page) {
	    int totalCount = mService.getApplyCount(id);
	    PageInfo pi =  Pagination.getPageInfo(page, totalCount, 5);
	    if (page < 1) page = 1;                      // 이전 없으면 1페이지 고정
	    if (page > pi.getMaxPage()) page = pi.getMaxPage(); // 다음 없으면 마지막 페이지 고정
	    mv.addObject("applyList", mService.getApplyList(pi, id));
	    mv.addObject("applyPi", pi);
	}

	public void setMyPostList(ModelAndView mv, String id, int page) {
	    int totalCount = mService.getMyPostCount(id);
	    PageInfo pi = Pagination.getPageInfo(page, totalCount, 5);
	    if (page < 1) page = 1;
	    if (page > pi.getMaxPage()) page = pi.getMaxPage();
	    mv.addObject("myPostList", mService.getMyPostList(pi, id));
	    mv.addObject("myPostPi", pi);
	}

	
	/*개인정보수정 페이지 이동*/
	@GetMapping("/edit")
	public String edit() {
		return "users/edit";
	}
	
	@PostMapping("/edit")
	public String updateMember(@ModelAttribute Member m, HttpSession session, Model model) {
	    // 비밀번호 입력했으면 암호화, 안했으면 null 유지
	    if(m.getUserPw() != null && !m.getUserPw().isBlank()) {
	        m.setUserPw(passwordEncoder.encode(m.getUserPw()));
	    }
	    
	    int result = mService.updateMember(m);
	    if(result > 0) {
	        Member updated = mService.getMemberById(m.getUserId());
	        model.addAttribute("loginUser", updated);
	        session.setAttribute("loginUser", updated);
	        return "redirect:/myPage";
	    }
	    return "users/edit";
	}

	/* 관리자페이지 */
	@GetMapping("/adminPage")
	public String adminPage(@ModelAttribute Member m, Model model, HttpSession session) {
		ArrayList<Member> mlist = mService.selectAll();
		model.addAttribute("mlist", mlist);
		ArrayList<Post> plist = pService.selectAllPost();
		model.addAttribute("plist",plist);
		ArrayList<Reply> rlist = rService.selectAllReply();
		model.addAttribute("replyList", rlist);
		Member loginUser = (Member)session.getAttribute("loginUser");
		if(loginUser != null &&  loginUser.getIsAdmin().equals("Y")) {     
			return "users/adminPage";
		}else {
			model.addAttribute("message", "올바른 접근이 아닙니다.");
			return "error/404";
		}
	}
	
	/* 회원 탈퇴*/
	@PostMapping("/deleteMember")
	@ResponseBody
	public int deleteMember(@ModelAttribute Member m, SessionStatus status) {
		int result = mService.deleteMember(m);
		if(result > 0) {
			status.setComplete();
			return result;
		}
		return result;
	}
	

	/* 회원 상태 변경(정지) */ 
	@PostMapping("/banMember")
	@ResponseBody
	public int banMember(@ModelAttribute Member m) {
		return mService.banMember(m);
	}
	
	/* 회원 상태 변경(활동) */
	@PostMapping("/activeMember")
	@ResponseBody
	public int activeMember(@ModelAttribute Member m) {
		return  mService.activeMember(m);
	}
	
	/* 회원 관리자료 변경 */
	@PostMapping("/toAdminMember")
	@ResponseBody
	public int toAdminMember(@ModelAttribute Member m) {
		return mService.toAdminMember(m);
	}
	
	/* 일반 회원으로 변경 */
	@PostMapping("/toNormalMember")
	@ResponseBody
	public int toNormalMember(@ModelAttribute Member m) {
		return mService.toNormalMember(m);
	}

}