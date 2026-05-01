package com.univol.post.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.univol.post.model.service.PostService;
import com.univol.post.model.service.ReplyService;
import com.univol.post.model.vo.Post;
import com.univol.post.model.vo.Reply;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@SessionAttributes("loginUser")
@RequiredArgsConstructor

public class PostController {
	private final PostService pService;
	private final ReplyService replyService;

	@GetMapping("/post")
	public String selectAll(Model model) {
		ArrayList<Post> plist = pService.selectAll();
		System.out.println("plist: " + plist);
		System.out.println("size: " + plist.size());
		System.out.println("posts" + plist);
		model.addAttribute("plist", plist);


		return "post/post";
	}

	@GetMapping("/post/{pNumber}")
	public String postDetail(@PathVariable(name = "pNumber") int pNumber, Model model, HttpSession session) {
		Post post = pService.selectOne(pNumber);
		model.addAttribute("post", post);

		// 댓글 목록 추가
		ArrayList<Reply> replyList = replyService.selectReplyList(pNumber);
		model.addAttribute("replyList", replyList);

		Object loginUser = session.getAttribute("loginUser");
		model.addAttribute("loginUser", loginUser);

		return "post/detail";
	}

}

