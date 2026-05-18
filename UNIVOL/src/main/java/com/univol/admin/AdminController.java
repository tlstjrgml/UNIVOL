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
import com.univol.member.vo.Member;
import com.univol.post.vo.Post;
import com.univol.post.vo.Reply;
import com.univol.admin.AdminService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@SessionAttributes("loginUser")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService aService;
	/* 관리자 페이지 */
	@GetMapping("/adminPage")
	public String adminPage(@ModelAttribute Member m, Model model, HttpSession session,
	        @RequestParam(value = "memberPage", defaultValue = "1") int memberPage,
	        @RequestParam(value = "postPage", defaultValue = "1") int postPage,
	        @RequestParam(value = "replyPage", defaultValue = "1") int replyPage) {

	    /* 관리자 권한 확인 */
	    Member loginUser = (Member) session.getAttribute("loginUser");
	    if (loginUser == null || loginUser.getIsAdmin().equals("N")) {
	        model.addAttribute("message", "올바른 접근이 아닙니다.");
	        return "error/404";
	    }

	    /* 회원 페이지네이션 */
	    int memberCount = aService.getMemberCount();
	    PageInfo memberPi = Pagination.getPageInfo(memberPage, memberCount, 10);
	    int startRow = (memberPi.getCurrentPage() - 1) * memberPi.getBoardLimit() + 1;
	    int endRow = memberPi.getCurrentPage() * memberPi.getBoardLimit();
	    ArrayList<Member> mlist = aService.selectAll(startRow, endRow);
	    model.addAttribute("mlist", mlist);
	    model.addAttribute("memberPi", memberPi);

	    /* 게시글 페이지네이션 */
	    int postCount = aService.getPostCount();
	    PageInfo postPi = Pagination.getPageInfo(postPage, postCount, 20);
	    int postStartRow = (postPi.getCurrentPage() - 1) * postPi.getBoardLimit() + 1;
	    int postEndRow = postPi.getCurrentPage() * postPi.getBoardLimit();
	    ArrayList<Post> plist = aService.selectAllPost(postStartRow, postEndRow);
	    model.addAttribute("plist", plist);
	    model.addAttribute("postPi", postPi);

	    /* 댓글 페이지네이션 */
	    int replyCount = aService.getReplyCount();
	    PageInfo replyPi = Pagination.getPageInfo(replyPage, replyCount, 20);
	    int replyStartRow = (replyPi.getCurrentPage() - 1) * replyPi.getBoardLimit() + 1;
	    int replyEndRow = replyPi.getCurrentPage() * replyPi.getBoardLimit();
	    ArrayList<Reply> rlist = aService.selectAllReply(replyStartRow, replyEndRow);
	    model.addAttribute("replyList", rlist);
	    model.addAttribute("replyPi", replyPi);

	    return "users/adminPage";
	}

	/* 회원 정지 */
	@PostMapping("/banMember")
	@ResponseBody
	public int banMember(@ModelAttribute Member m) {
		return aService.banMember(m);
	}

	/* 회원 활동 복구 */
	@PostMapping("/activeMember")
	@ResponseBody
	public int activeMember(@ModelAttribute Member m) {
		return aService.activeMember(m);
	}

	/* 회원 관리자 권한 부여 */
	@PostMapping("/toAdminMember")
	@ResponseBody
	public int toAdminMember(@ModelAttribute Member m) {
		return aService.toAdminMember(m);
	}

	/* 회원 일반 권한으로 변경 */
	@PostMapping("/toNormalMember")
	@ResponseBody
	public int toNormalMember(@ModelAttribute Member m) {
		return aService.toNormalMember(m);
	}

	/* 게시글 삭제 */
	@PostMapping("/deletePost")
	@ResponseBody
	public int deletePost(@ModelAttribute Post p) {
		return aService.deletePost(p);
	}

	/* 게시글 복구 */
	@PostMapping("/rollbackPost")
	@ResponseBody
	public int rollbackPost(@ModelAttribute Post p) {
		return aService.rollbackPost(p);
	}

	/* 댓글 삭제 */
	@PostMapping("/adminDeleteReply")
	@ResponseBody
	public int adminDeleteReply(@RequestParam("rNumber") int rNumber) {
		return aService.adminDeleteReply(rNumber);
	}

	/* 댓글 복구 */
	@PostMapping("/adminRollbackReply")
	@ResponseBody
	public int adminRollbackReply(@RequestParam("rNumber") int rNumber) {
		return aService.adminRollbackReply(rNumber);
	}
}