package com.univol.review.service;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.univol.common.PageInfo;
import com.univol.review.vo.ReviewReply;
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

	public ArrayList<Review> selectReviewList(PageInfo pi, char c, String sort) {
	    int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
	    RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
	    return mapper.selectReviewList(c, sort, rowBounds);
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

	public int getSearchCount(String keyword) {
		return mapper.getSearchCount(keyword);
	}


	public ArrayList<Review> searchReviews(String keyword, String sort, PageInfo pi) {
	    int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
	    RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
	    return mapper.searchReviews(keyword, sort, rowBounds);
	}
	
}
