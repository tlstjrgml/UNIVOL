package com.univol.review.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.univol.review.model.vo.Review;

@Mapper
public interface ReviewMapper {

	int getListCount(char c);

	ArrayList<Review> selectReviewList(char c, RowBounds rowBounds);

	Review selectReview(int bId);

	int updateReview(int bId);

	int updateReviews(Review r);

	int insertReview(Review r);

}
