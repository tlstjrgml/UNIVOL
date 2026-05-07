package com.univol.review.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.univol.member.model.vo.Member;
import com.univol.member.model.vo.PageInfo;
import com.univol.review.model.exception.ReviewException;
import com.univol.review.model.service.ReviewService;
import com.univol.review.model.vo.Review;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
	
	@GetMapping("/{id}/{page}")
	public String selectReview(@PathVariable("id") int bId, @PathVariable("page") int page,
							HttpSession session, Model model) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		String id = null;
		if(loginUser != null) {
			id = loginUser.getUserId();
		}
		
		Review r = rService.selectReview(bId, id);
		if(r != null) {
			model.addAttribute("r", r);
			model.addAttribute("page", page);
			return "review/detail";
		} else {
			throw new ReviewException("게시글 상세보기를 실패하였습니다.");
		}
	}
	
	@GetMapping("/detail/{rNumber}/{page}")
	public String detail(@PathVariable("rNumber") int rNumber,
	                     @PathVariable("page") int page,
	                     Model model) {

	    Review r = rService.selectReview(rNumber, null);

	    model.addAttribute("r", r);
	    model.addAttribute("page", page);

	    return "review/detail";
	}
	
	@PostMapping("/update")
	public String updateReview(@ModelAttribute Review r) {

	    int result = rService.updateReviews(r);

	    if (result > 0) {
	    	return "redirect:/review/detail/" + r.getRNumber() + "/" + 1;
	    } else {
	        throw new ReviewException("수정 실패");
	    }
	}
	
	
	
	
}
