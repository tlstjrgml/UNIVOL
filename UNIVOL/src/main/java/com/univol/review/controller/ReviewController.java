package com.univol.review.controller;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.univol.post.model.exception.PostException;
import com.univol.review.exception.ReviewException;
import com.univol.review.service.ReviewService;
import com.univol.review.vo.Review;
import com.univol.review.vo.ReviewReply;
import com.univol.post.vo.Post;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

	private final ReviewService rService;

	/* 후기게시판 목록 조회 */
	@GetMapping("")
	public ModelAndView selectList(
			@RequestParam(value = "page", defaultValue = "1") int currentPage,
			@RequestParam(value = "sort", defaultValue = "latest") String sort,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			ModelAndView mv, HttpServletRequest request) {

		int listCount;
		if (keyword.isEmpty()) {
			listCount = rService.getListCount('R');
		} else {
			listCount = rService.getSearchCount(keyword);
		}

		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

		ArrayList<Review> list;
		if (keyword.isEmpty()) {
			list = rService.selectReviewList(pi, 'R', sort);
		} else {
			list = rService.searchReviews(keyword, sort, pi);
		}

		mv.addObject("loc", request.getRequestURI());
		mv.addObject("list", list);
		mv.addObject("pi", pi);
		mv.addObject("keyword", keyword);
		mv.addObject("sort", sort);
		mv.setViewName("review/review");
		return mv;
	}

	/* 후기 작성 페이지 이동 */
	@GetMapping("write")
	public String writeReview(HttpSession session, Model model) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		if(loginUser != null) {
			ArrayList<Post>applyList = rService.selectApplyList(loginUser.getUserId());
			model.addAttribute("applyList", applyList);
			return "review/write";
		}else {
			throw new ReviewException("로그인이 필요한 페이지입니다");
		}
		
	}

	/* 후기 작성 처리 */
	@PostMapping("insert")
	public String insertReview(@ModelAttribute Review r, HttpSession session) {
		String ReviewWriter = ((Member) session.getAttribute("loginUser")).getUserId();
		r.setUserId(ReviewWriter);
		r.setPType("R");

		int result = rService.insertReview(r);
		if (result > 0) {
			return "redirect:/review";
		} else {
			throw new ReviewException("게시글 작성을 실패했습니다.");
		}
	}

	/* 후기 상세 조회 */
	@GetMapping("/{id}/{page}")
	public String selectReview(@PathVariable("id") int bId, 
	        @PathVariable("page") int page,
	        @RequestParam(value = "sort", defaultValue = "latest") String sort,
	        @RequestParam(value = "keyword", defaultValue = "") String keyword,
	        HttpSession session, Model model) {
	    Member loginUser = (Member) session.getAttribute("loginUser");
	    if(loginUser == null) {
	        throw new PostException("로그인이 필요한 페이지입니다");
	    }
	    String id = loginUser.getUserId();

	    int likeCount = rService.likeCount(bId);
	    int isLiked = rService.reviewLike(bId, id);

	    Review r = rService.selectReview(bId, id);
	    ArrayList<ReviewReply> list = rService.selectReplyList(bId);
	    if (r != null) {
	        model.addAttribute("isLiked", isLiked);
	        model.addAttribute("likeCount", likeCount);
	        model.addAttribute("r", r);
	        model.addAttribute("page", page);
	        model.addAttribute("currentPage", page);
	        model.addAttribute("sort", sort);
	        model.addAttribute("keyword", keyword);
	        model.addAttribute("list", list);
	        return "review/detail";
	    } else {
	        throw new ReviewException("게시글 상세보기를 실패하였습니다.");
	    }
	}

	/* 수정 후 상세 조회 */
	@GetMapping("/detail/{rNumber}/{page}")
	public String detail(@PathVariable("rNumber") int rNumber,
			@PathVariable("page") int page,
			Model model) {

		Review r = rService.selectReview(rNumber, null);
		ArrayList<ReviewReply> list = rService.selectReplyList(rNumber);
		model.addAttribute("r", r);
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		return "review/detail";
	}

	/* 후기 수정 처리 */
	@PostMapping("/update")
	public String updateReview(@ModelAttribute Review r,
			@RequestParam("page") int page,
			HttpSession session) {

		Member loginMember = (Member) session.getAttribute("loginUser");
		if (loginMember == null) {
			throw new ReviewException("로그인이 필요합니다.");
		}

		Review origin = rService.selectReview(r.getRNumber(), null);
		if (origin == null) {
			throw new ReviewException("게시글이 존재하지 않습니다.");
		}

		if (!origin.getUserId().equals(loginMember.getUserId())) {
			throw new ReviewException("본인 글만 수정 가능합니다.");
		}

		r.setUserId(loginMember.getUserId());
		int result = rService.updateReviews(r);

		if (result > 0) {
			return String.format("redirect:/review/detail/%d/%d", r.getRNumber(), page);
		} else {
			throw new ReviewException("수정 실패");
		}
	}

	/* 메인페이지 후기 TOP 5 */
	@GetMapping("top")
	@ResponseBody
	public ArrayList<Review> selectTop(){
		ArrayList<Review> list = rService.selectTop();
		return list;
	}

	/* 후기 삭제 */
	@PostMapping("delete")
	public String deleteReview(@RequestParam("rNumber") int rNumber,
			@RequestParam("page") int page, HttpSession session) {
		int result = rService.deleteReview(rNumber);
		if (result > 0) {
			return "redirect:/review";
		} else {
			throw new ReviewException("삭제 실패");
		}
	}

	/* 댓글 등록 */
	@GetMapping("rinsert")
	@ResponseBody
	public ArrayList<ReviewReply> insertReply(@ModelAttribute ReviewReply r) {
		int result = rService.insertReply(r);
		ArrayList<ReviewReply> list = rService.selectReplyList(r.getPNumber());
		System.out.println(r.getUserId());
		return list;
	}

	/* 좋아요 토글 */
	@PostMapping("like")
	@ResponseBody
	public HashMap<String, Object> reviewLike(@RequestParam("pNumber") int pNumber, @RequestParam("userId") String userId) {
		int result = rService.reviewLike(pNumber, userId);
		if (result > 0) {
			rService.deleteLike(pNumber, userId);
		} else {
			rService.insertLike(pNumber, userId);
		}
		int likeCount = rService.likeCount(pNumber);

		HashMap<String, Object> map = new HashMap<>();
		map.put("liked", result == 0);
		map.put("likeCount", likeCount);
		return map;
	}

	/* 댓글 수정 */
	@PostMapping("reviewReply/update")
	public String reupdate(ReviewReply reply, @RequestParam("pNumber") int pno,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		int result = rService.reviewUpdate(reply);
		if (result > 0) {
			return "redirect:/review/" + pno + "/" + page;
		} else {
			throw new ReviewException("수정 실패");
		}
	}

	/* 댓글 삭제 */
	@PostMapping("/reviewReply/delete")
	public String redelete(@RequestParam("cNumber") int cnum, @RequestParam("rNumber") int rnum,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		int result = rService.reviewDelete(cnum);
		if (result > 0) {
			return "redirect:/review/detail/" + rnum + "/" + page;
		} else {
			throw new ReviewException("삭제 실패");
		}
	}
	
	/* 검색 */
	
	
}