package com.univol.review.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.univol.common.PageInfo;
import com.univol.review.mapper.ReviewMapper;
import com.univol.review.vo.Review;
import com.univol.review.vo.ReviewReply;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewMapper mapper;

	/* 목록 조회 */
	public int getListCount(char c) {
		return mapper.getListCount(c);
	}

	public ArrayList<Review> selectReviewList(PageInfo pi, char c, String sort) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectReviewList(c, sort, rowBounds);
	}

	/* 상세 조회 */
	public Review selectReview(int bId, String id) {
		Review r = mapper.selectReview(bId);
		if (r != null) {
			if (id != null && !r.getUserId().equals(id)) {
				int result = mapper.updateView(bId);
				if (result > 0) {
					r.setRViews(r.getRViews() + 1);
				}
			}
		}
		return r;
	}

	/* 등록 */
	public int insertReview(Review r) {
		return mapper.insertReview(r);
	}

	/* 수정 */
	public int updateReviews(Review r) {
		return mapper.updateReviews(r);
	}

	/* 삭제 */
	public int deleteReview(int rNumber) {
		return mapper.deleteReview(rNumber);
	}

	/* 검색 */
	public int getSearchCount(String keyword) {
		return mapper.getSearchCount(keyword);
	}

	public ArrayList<Review> searchReviews(String keyword, String sort, PageInfo pi) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.searchReviews(keyword, sort, rowBounds);
	}

	public int searchCount(String keyword) {
		return mapper.searchCount(keyword);
	}

	public ArrayList<Review> searchReview(String keyword, PageInfo pi) {
		return mapper.searchReview(keyword, pi);
	}

	/* 메인페이지 TOP 5 */
	public ArrayList<Review> selectTop() {
		return mapper.selectTop();
	}

	/* 댓글 */
	public ArrayList<ReviewReply> selectReplyList(int bId) {
		return mapper.selectReplyList(bId);
	}

	public int insertReply(ReviewReply r) {
		return mapper.insertReply(r);
	}

	public int reviewUpdate(ReviewReply reply) {
		return mapper.reviewUpdate(reply);
	}

	public int reviewDelete(int cnum) {
		return mapper.reviewDelete(cnum);
	}

	/* 좋아요 */
	public int reviewLike(int pNumber, String userId) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("pNumber", pNumber);
		map.put("userId", userId);
		return mapper.reviewLike(map);
	}

	public int deleteLike(int pNumber, String userId) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("pNumber", pNumber);
		map.put("userId", userId);
		return mapper.deleteLike(map);
	}

	public int insertLike(int pNumber, String userId) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("pNumber", pNumber);
		map.put("userId", userId);
		return mapper.insertLike(map);
	}

	public int likeCount(int pNumber) {
		return mapper.likeCount(pNumber);
	}
}