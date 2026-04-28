package com.univol.post.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.univol.post.model.service.PostService;
import com.univol.post.model.vo.Post;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@SessionAttributes("loginUser")
@RequiredArgsConstructor

public class PostController {
	private final PostService pService;
	
	@GetMapping("/volunteer")
	public String selectAll(Model model) {
		ArrayList<Post> plist = pService.selectAll();
		System.out.println("plist: " + plist);
		System.out.println("size: " + plist.size());
		System.out.println("posts" + plist);
		model.addAttribute("plist", plist);
		
		
		return "post/post";
	}
	

	}

