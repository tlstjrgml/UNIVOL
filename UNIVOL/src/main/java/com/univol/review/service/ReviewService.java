package com.univol.review.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.univol.common.PageInfo;
import com.univol.review.mapper.ReviewMapper;
import com.univol.review.vo.Review;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewMapper mapper;
 
	public int getListCount(char c) {
		return mapper.getListCount(c);
	}

	public ArrayList<Review> selectReviewList(PageInfo pi, char c) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectReviewList(c, rowBounds);
	}

	public Review selectReview(int bId, String id) {
		Review r = mapper.selectReview(bId);
		if(r != null) {
			if(id != null && !r.getUserId().equals(id)) {
				int result = mapper.updateView(bId);
				if(result > 0) {
					r.setRViews(r.getRViews() + 1);
				}
			}
		}
		return r;
	}

	public int updateReviews(Review r) {
		return mapper.updateReviews(r);
	}

	public int insertReview(Review r) {
		return mapper.insertReview(r);  
	}

	public ArrayList<Review> selectTop() {
		return mapper.selectTop();
	}

	public ArrayList<com.univol.review.vo.ReviewReply> selectReplyList(int bId) {
		return mapper.selectReplyList(bId);
	}

	public int insertReply(com.univol.review.vo.ReviewReply r) {
		return mapper.insertReply(r);
	}

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
