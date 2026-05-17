package com.univol.post.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.univol.common.PageInfo;
import com.univol.common.Pagination;
import com.univol.member.vo.Member;
import com.univol.post.model.exception.PostException;
import com.univol.post.service.PostService;
import com.univol.post.service.ReplyService;
import com.univol.post.vo.Post;
import com.univol.post.vo.Reply;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@SessionAttributes("loginUser")
@RequiredArgsConstructor
public class PostController {

	private final PostService pService;
	private final ReplyService rService;

	/* 게시글 목록 조회 */
	@GetMapping("/post")
	public String selectAll(Model model,
			@RequestParam(value = "page", defaultValue = "1") int currentPage,
			@RequestParam(value = "sort", defaultValue = "latest") String sort,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {

		int listCount;
		if (keyword.isEmpty()) {
			listCount = pService.getListCount();
		} else {
			listCount = pService.getSearchCount(keyword);
		}

		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

		int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
		int endRow = pi.getCurrentPage() * pi.getBoardLimit();

		ArrayList<Post> plist;
		if (keyword.isEmpty()) {
			plist = pService.selectAll(startRow, endRow, sort);
		} else {
			plist = pService.searchPosts(keyword, sort, startRow, endRow);
		}

		model.addAttribute("pi", pi);
		model.addAttribute("plist", plist);
		model.addAttribute("keyword", keyword);
		model.addAttribute("sort", sort);

		return "post/post";
	}

	/* 글 작성 페이지 이동 */
	@GetMapping("/post/write")
	public String postWrite(HttpSession session) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		if(loginUser != null) {
			return "post/write";
		}else {
			throw new PostException("로그인이 필요한 페이지입니다");
		}
	}

	/* 글 작성 처리 */
	@PostMapping("/post/write")
	public String insertPost(@ModelAttribute Post p, HttpSession session,
			@RequestParam(value = "isNotice", defaultValue = "N") String isNotice) {
		Member loginUser = (Member) session.getAttribute("loginUser");
		p.setPType('V');
		p.setUserId(loginUser.getUserId());
		if (p.getIsNotice() == null) {
			p.setIsNotice("N");
		}
		pService.insertPost(p);
		return "redirect:/post";
	}

	/* 글 상세 조회 */
	@GetMapping({"/post/{currentPage}/{pNumber}", "/post/{pNumber}"})
	public String selectOne(
			@PathVariable(value = "currentPage", required = false) Integer currentPage,
			@PathVariable("pNumber") int pNumber,
			Model model, HttpSession session,
			@RequestParam(value = "sort", defaultValue = "latest") String sort,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {

		Member loginUser = (Member) session.getAttribute("loginUser");
		if(loginUser== null) {
			throw new PostException ("로그인이 필요한 페이지입니다");
		}
		String userId = null;
		if (loginUser != null) {
			userId = loginUser.getUserId();
		}
		int page = (currentPage == null) ? 1 : currentPage;

		Post post = pService.selectOne(pNumber, userId);
		if (post == null) {
			return "error/404";
		}
		

		int likeCount = pService.likeCount(pNumber);
		int isLiked = pService.postLike(pNumber, userId);
		int isApplied = pService.checkApply(pNumber, userId);
		ArrayList<Reply> replyList = rService.selectReplyList(pNumber);

		model.addAttribute("likeCount", likeCount);
		model.addAttribute("isLiked", isLiked);
		model.addAttribute("post", post);
		model.addAttribute("replyList", replyList);
		model.addAttribute("currentPage", page);
		model.addAttribute("sort", sort);
		model.addAttribute("keyword", keyword);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("isApplied", isApplied);
		return "post/detail";
	}

	/* 메인페이지 봉사게시판 TOP 5 */
	@GetMapping("/post/top")
	@ResponseBody
	public ArrayList<Post> selectTopPost() {
		return pService.selectTopPost();
	}

	/* 글 삭제 (작성자) */
	@PostMapping("/post/delete/{pNumber}")
	public String userDeletePost(@PathVariable("pNumber") int pNumber) {
		int result = pService.userDeletePost(pNumber);
		if (result > 0) {
			return "redirect:/post";
		} else {
			throw new PostException("게시글 삭제에 실패했습니다");
		}
	}

	/* 글 수정 (작성자) */
	@PostMapping("/post/edit/{pNumber}")
	public String userEditPost(
			@PathVariable("pNumber") int pNumber,
			@ModelAttribute Post p,
			@RequestParam("currentPage") int currentPage) {

		p.setPNumber(pNumber);
		int result = pService.userEditPost(p);

		if (result > 0) {
			return "redirect:/post/" + currentPage + "/" + pNumber;
		} else {
			throw new PostException("게시글 수정을 실패했습니다");
		}
	}

	/* 좋아요 토글 */
	@PostMapping("/post/like")
	@ResponseBody
	public HashMap<String, Object> postLike(@RequestParam("pNumber") int pNumber, @RequestParam("userId") String userId) {
		int result = pService.postLike(pNumber, userId);
		if (result > 0) {
			pService.deleteLike(pNumber, userId);
		} else {
			pService.insertLike(pNumber, userId);
		}
		int likeCount = pService.likeCount(pNumber);

		HashMap<String, Object> map = new HashMap<>();
		map.put("liked", result == 0);
		map.put("likeCount", likeCount);
		return map;
	}
	
	/*참여 토글 */
	@PostMapping("/post/apply")
	@ResponseBody
	public int apply(@RequestParam("pNumber") int pNumber, @RequestParam("userId") String userId) {
		int result = pService.checkApply(pNumber, userId);
		if(result > 0) {
			pService.cancelApply(pNumber, userId);
		}else {
			pService.insertApply(pNumber, userId);
		}return result;
	}
}