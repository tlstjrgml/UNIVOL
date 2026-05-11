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

import com.univol.member.vo.Member;
import com.univol.post.service.PostService;
import com.univol.post.service.ReplyService;
import com.univol.common.PageInfo;
import com.univol.common.Pagination;
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
			listCount = pService.getSearchCount(keyword); // 검색 결과 갯수(페이지네이션)
		}
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount,10);
		
		int startRow = (pi.getCurrentPage()-1)*pi.getBoardLimit()+1;
		int endRow = pi.getCurrentPage() * pi.getBoardLimit();
		
		ArrayList<Post> plist;
		if(keyword.isEmpty()) {
			plist = pService.selectAll(startRow, endRow, sort);
		}else {
			plist = pService.searchPosts(keyword, sort, startRow, endRow); // 검색 글 목록
		}
		
		
		model.addAttribute("pi",pi);
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
	public String insertPost(@ModelAttribute Post p, HttpSession session) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		p.setPType('V');
		p.setUserId(loginUser.getUserId());
		pService.insertPost(p);
		
		
		return "redirect:/post";
	}

	/* 글 상세조회 */
	@GetMapping("/post/{currentPage}/{pNumber}")
	public String selectOne(@PathVariable("currentPage") int currentPage, @PathVariable("pNumber") int pNumber, Model model, HttpSession session) {
	    Member loginUser = (Member)session.getAttribute("loginUser");
	    String userId = null;
	    if(loginUser != null) {
	    	userId = loginUser.getUserId();
	    }
		Post post = pService.selectOne(pNumber, userId);
	    if(post == null) {
	        return "error/404";
	    }
	    ArrayList<Reply> replyList = rService.selectReplyList(pNumber);
	    model.addAttribute("post", post);
	    model.addAttribute("replyList",replyList );
	    model.addAttribute("currentPage", currentPage);
	    return "post/detail";
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
	
	
	
	
	
	
	
	
	
}


 

