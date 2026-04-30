package com.univol.review.model.service;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.univol.member.model.vo.PageInfo;
import com.univol.review.mapper.ReviewMapper;
import com.univol.review.model.vo.Review;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewMapper mapper;

	public int getListCount(char c) {
		return mapper.getListCount(c);
	}

	public ArrayList<Review> selectReviewList(PageInfo pi, char c) {
		int offset = (pi.getCurrentPage() - 1) * pi.getReviewLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getReviewLimit());
		return mapper.selectReviewList(c, rowBounds);
	}

	public Review selectReview(int bId, String id) {
		Review r = mapper.selectReview(bId);
		if(r != null) {
			if(id != null && !r.getUserId().equals(id)) {
				int result = mapper.updateRViews(bId);
				if(result > 0) {
					r.setRViews(r.getRViews() + 1);
				}
			}
		}
		return r;
	}
}
