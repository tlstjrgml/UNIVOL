package com.univol.admin;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

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
public class AdminController {
	private final MemberService mService;
	private final PostService pService;
	private final ReplyService rService;
	
	/* 관리자페이지 */
	@GetMapping("/adminPage")
	public String adminPage(@ModelAttribute Member m, Model model, HttpSession session,
			@RequestParam(value="memberPage", defaultValue="1")int memberPage,
			@RequestParam(value="postPage", defaultValue="1") int postPage,
			@RequestParam(value="replyPage", defaultValue="1") int replyPage
			) {
		//회원페이지네이션
		int memberCount = mService.getMemberCount();
		PageInfo memberPi = Pagination.getPageInfo(memberPage, memberCount, 10);
		int startRow = (memberPi.getCurrentPage()-1)*memberPi.getBoardLimit()+1;
		int endRow = memberPi.getCurrentPage() * memberPi.getBoardLimit();
		ArrayList<Member> mlist = mService.selectAll(startRow, endRow);
		model.addAttribute("mlist", mlist);
		model.addAttribute("memberPi", memberPi);
		
		//게시글 페이지네이션
		int postCount = pService.getPostCount();
		PageInfo postPi = Pagination.getPageInfo(postPage, postCount, 20);
		int postStartRow = (postPi.getCurrentPage()-1)*postPi.getBoardLimit()+1;
		int postEndRow = postPi.getCurrentPage() * postPi.getBoardLimit();
		ArrayList<Post> plist = pService.selectAllPost(postStartRow, postEndRow);
		model.addAttribute("plist", plist);
		model.addAttribute("postPi", postPi);
		
		//댓글 페이지네이션
		int replyCount = rService.getReplyCount();
		PageInfo replyPi = Pagination.getPageInfo(replyPage, replyCount, 20);
		int replyStartRow = (replyPi.getCurrentPage()-1)*replyPi.getBoardLimit()+1;
		int replyEndRow = replyPi.getCurrentPage() * replyPi.getBoardLimit();
		ArrayList<Reply> rlist = rService.selectAllReply(replyStartRow, replyEndRow);
		model.addAttribute("replyList", rlist);
		model.addAttribute("rlist", rlist);
		model.addAttribute("replyPi", replyPi);
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		if(loginUser != null &&  loginUser.getIsAdmin().equals("Y")) {     
			return "users/adminPage";
		}else {
			model.addAttribute("message", "올바른 접근이 아닙니다.");
			return "error/404";
		}
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
	
	/* 관리자페이지에서 글 삭제하기 */
	@PostMapping("/deletePost")
	@ResponseBody
	public int deletePost(@ModelAttribute Post p) {
	    int result = pService.deletePost(p);
	    if(result > 0) {
	        return result;
	    }
	    return result;
	}

	/* 관리자페이지에서 글 복구하기 */
	@PostMapping("/rollbackPost")
	@ResponseBody
	public int rollbackPost(@ModelAttribute Post p) {
	    int result = pService.rollbackPost(p);
	    if(result > 0) {
	        return result;
	    }
	    return result;
	}	
	
	@PostMapping("/adminDeleteReply")
    @ResponseBody
    public int adminDeleteReply(@RequestParam("rNumber") int rNumber) {
    	int result=rService.adminDeleteReply(rNumber);	
    	if(result>0) {
    		return result;
    	}return result;
    }
    
    @PostMapping("/adminRollbackReply")
    @ResponseBody
    public int adminRollbackReply(@RequestParam("rNumber") int rNumber) {
    	int result=rService.adminRollbackReply(rNumber);
    	if(result > 0) {
    		return result;
    	}return result;
    }
}
