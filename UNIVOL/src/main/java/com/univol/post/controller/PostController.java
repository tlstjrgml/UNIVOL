package com.univol.post.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.univol.member.model.vo.Member;
import com.univol.post.Pagenation;
import com.univol.post.model.service.PostService;
import com.univol.post.model.service.ReplyService;
import com.univol.post.model.vo.PageInfo;
import com.univol.post.model.vo.Post;
import com.univol.post.model.vo.Reply;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@SessionAttributes("loginUser")
@RequiredArgsConstructor

public class PostController {
	private final PostService pService;
	private final ReplyService rService;
	
	@GetMapping("/post")
	public String selectAll(Model model, @RequestParam (value = "page", defaultValue="1") int currentPage) {
		PageInfo pi = Pagenation.getPageInfo(currentPage, pService.getListCount(),10);
		model.addAttribute("pi",pi);

		int startRow = (pi.getCurrentPage()-1)*pi.getBoardLimit()+1;
		int endRow = pi.getCurrentPage() * pi.getBoardLimit();
		ArrayList<Post> plist = pService.selectAll(startRow, endRow);
		model.addAttribute("plist", plist);
		
		
		return "post/post";
	}

	@GetMapping("/post/write")
	public String postWrite() {
		return "post/write";
	}
	
	@PostMapping("/post/write")
	public String insertPost(@ModelAttribute Post p, HttpSession session) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		p.setPType('V');
		p.setUserId(loginUser.getUserId());
		pService.insertPost(p);
		return "redirect:/post";
	}
	
	@GetMapping("/post/{currentPage}/{pNumber}")
	public String selectOne(@PathVariable("currentPage") int currentPage, @PathVariable("pNumber") int pNumber, Model model) {

		@GetMapping("/post/{pNumber}")
	public String selectOne( @PathVariable("pNumber") int pNumber, Model model) {
		Post post = pService.selectOne(pNumber);
		ArrayList<Reply> replyList = rService.selectReplyList(pNumber);
		model.addAttribute("post",post);
		model.addAttribute("replyList", replyList);
		return "post/detail";
	}
}
	
	
	





 