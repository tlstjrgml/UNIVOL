package com.univol.post.controller;

import java.util.ArrayList;

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

	/* 게시글 페이지로 이동 */
	@GetMapping("/post")
	public String selectAll(Model model, 
			@RequestParam (value = "page", defaultValue="1") int currentPage,
			@RequestParam(value = "sort", defaultValue="latest") String sort,
			@RequestParam(value="keyword", defaultValue="") String keyword) {
		
		int listCount;
		if(keyword.isEmpty()) {
			listCount = pService.getListCount();
		}else {
			listCount = pService.getSearchCount(keyword);
		}
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
		
		int startRow = (pi.getCurrentPage()-1)*pi.getBoardLimit()+1;
		int endRow = pi.getCurrentPage() * pi.getBoardLimit();
		
		ArrayList<Post> plist;
		if(keyword.isEmpty()) {
			plist = pService.selectAll(startRow, endRow, sort);
		}else {
			plist = pService.searchPosts(keyword, sort, startRow, endRow);
		}
		
		model.addAttribute("pi", pi);
		model.addAttribute("plist", plist);
		model.addAttribute("keyword", keyword);
		model.addAttribute("sort", sort);
		
		return "post/post";
	}

	/* 글 작성하는 뷰로 이동하기 */
	@GetMapping("/post/write")
	public String postWrite() {
		return "post/write";
	}

	/* 글 작성을 완료하면 post페이지로 보내기 */
	@PostMapping("/post/write")
	public String insertPost(@ModelAttribute Post p, HttpSession session,
			@RequestParam(value="isNotice", defaultValue="N") String isNotice) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		p.setPType('V');
		p.setUserId(loginUser.getUserId());
		if(p.getIsNotice() == null) {
			p.setIsNotice("N");
		}
		pService.insertPost(p);
		return "redirect:/post";
	}

	/* 글 상세조회 */
	@GetMapping({"/post/{currentPage}/{pNumber}", "/post/{pNumber}"})
	public String selectOne(@PathVariable(value="currentPage", required=false) Integer currentPage, @PathVariable("pNumber") int pNumber, Model model, HttpSession session,
							@RequestParam(value="sort", defaultValue="latest") String sort,
							@RequestParam(value="keyword", defaultValue="") String keyword) {
	    Member loginUser = (Member)session.getAttribute("loginUser");
	    String userId = null;
	    if(loginUser != null) {
	    	userId = loginUser.getUserId();
	    }
	    
	    int page = (currentPage == null) ? 1 :currentPage;
	    
		Post post = pService.selectOne(pNumber, userId);
	    if(post == null) {
	        return "error/404";
	    }
	    ArrayList<Reply> replyList = rService.selectReplyList(pNumber);
	    model.addAttribute("post", post);

	    model.addAttribute("replyList", replyList);
	    model.addAttribute("currentPage", currentPage);
	    model.addAttribute("sort", sort);
	    model.addAttribute("keyword", keyword);
	    model.addAttribute("loginUser", loginUser);
	    return "post/detail";
	}

	/* 메인페이지 봉사게시판 5개 보여주기 */
	@GetMapping("/post/top")
	@ResponseBody
	public ArrayList<Post> selectTopPost(){
		ArrayList<Post> list = pService.selectTopPost();
		return list;
	}
	


 

	/* 사용자 글 삭제 */
	@PostMapping("/post/delete/{pNumber}")
	public String userDeletePost(@PathVariable("pNumber") int pNumber) {
		int result = pService.userDeletePost(pNumber);
		if(result>0) {
			return "redirect:/post";
		}else {
			throw new PostException("게시글 삭제에 실패했습니다");
		}
		
	
	}
}
