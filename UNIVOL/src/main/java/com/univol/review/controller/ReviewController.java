package com.univol.review.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.univol.member.model.vo.PageInfo;
import com.univol.review.model.service.ReviewService;
import com.univol.review.model.vo.Review;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import templates.common.Pagination;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
	private final ReviewService rService;
	
	@GetMapping("")
	public ModelAndView selectList(@RequestParam(value="page", defaultValue="1") int currentPage,
							ModelAndView mv, HttpServletRequest request) {
		int listCount = rService.getListCount('R');
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
		ArrayList<Review> list = rService.selectReviewList(pi, 'R');
		
		mv.addObject("loc", request.getRequestURI());
		mv.addObject("list", list).addObject("pi", pi).setViewName("review/review");
		return mv;
	}
	
	
	
	
	
	
}
