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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.univol.common.PageInfo;
import com.univol.common.Pagination;
import com.univol.member.vo.Member;
import com.univol.review.exception.ReviewException;
import com.univol.review.service.ReviewService;
import com.univol.review.vo.ReviewReply;
import com.univol.review.vo.Review;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

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
	
	@GetMapping("write")
	public String writeReview() {
		return "review/write";
	}
	
	@PostMapping("insert")
	public String insertReview(@ModelAttribute Review r, HttpSession session) {
		String ReviewWriter = ((Member)session.getAttribute("loginUser")).getUserId();
		r.setUserId(ReviewWriter);
		r.setPType("R");
		
		int result = rService.insertReview(r); 
		if(result > 0) {
			return "redirect:/review";
		} else {
			throw new ReviewException("게시글 작성을 실패했습니다.");
		}
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
		ArrayList<ReviewReply> list = rService.selectReplyList(bId);
		if(r != null) {
			model.addAttribute("r", r);
			model.addAttribute("page", page);
			model.addAttribute("list", list);
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
	public String updateReview(@ModelAttribute Review r,
	                           @RequestParam("page") int page,
	                           HttpSession session) {

	    // 로그인 회원 꺼내기 (Member)
	    Member loginMember = (Member) session.getAttribute("loginUser");

	    if (loginMember == null) {
	        throw new ReviewException("로그인이 필요합니다.");
	    }

	    // 기존 게시글 조회
	    Review origin = rService.selectReview(r.getRNumber(), null);

	    if (origin == null) {
	        throw new ReviewException("게시글이 존재하지 않습니다.");
	    }

	    // 권한 체크 (내 글인지 확인)
	    if (!origin.getUserId().equals(loginMember.getUserId())) {
	        throw new ReviewException("본인 글만 수정 가능합니다.");
	    }

	    // 서버에서 작성자 강제 세팅 (보안)
	    r.setUserId(loginMember.getUserId());

	    // 수정 실행
	    int result = rService.updateReviews(r);

	    if (result > 0) {
	    	return String.format("redirect:/review/detail/%d/%d", r.getRNumber(), page);
	    } else {
	        throw new ReviewException("수정 실패");
	    }
	}
	
	@GetMapping("top")
	@ResponseBody
	public ArrayList<Review> selectTop(){
		ArrayList<Review> list = rService.selectTop();
		return list;
	}
	
	@GetMapping("rinsert")
	@ResponseBody
	public ArrayList<ReviewReply> insertReply(@ModelAttribute ReviewReply r){
		int result = rService.insertReply(r);
		ArrayList<ReviewReply> list = rService.selectReplyList(r.getPNumber());
		System.out.println(r.getUserId());
		return list;
	}
	
	
	
	
}
