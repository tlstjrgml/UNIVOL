package com.univol.review.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.univol.review.vo.Review;

@Mapper
public interface ReviewMapper {

	int getListCount(char c);

	ArrayList<Review> selectReviewList(@Param("pType") char c, @Param("sort") String sort,  RowBounds rowBounds);

	Review selectReview(int bId);

	int updateView(int bId);

	int updateReviews(Review r);

	int insertReview(Review r);

	ArrayList<Review> selectTop();

	ArrayList<com.univol.review.vo.ReviewReply> selectReplyList(int bId);

	int insertReply(com.univol.review.vo.ReviewReply r);

	int getSearchCount(@Param("keyword") String keyword);

	ArrayList<Review> searchReviews(@Param("keyword") String keyword,  @Param("sort") String sort, RowBounds rowBounds);
	

}
