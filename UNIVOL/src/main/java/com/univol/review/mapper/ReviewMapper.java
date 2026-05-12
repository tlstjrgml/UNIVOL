package com.univol.review.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.univol.review.vo.Review;
import com.univol.review.vo.ReviewReply;

@Mapper
public interface ReviewMapper {

	int getListCount(char c);

	ArrayList<Review> selectReviewList(@Param("pType") char c, @Param("sort") String sort, RowBounds rowBounds);

	Review selectReview(int bId);

	int updateReview(int bId);

	int updateView(int bId);

	int updateReviews(Review r);

	int insertReview(Review r);

	ArrayList<Review> selectTop();

	ArrayList<ReviewReply> selectReplyList(int bId);

	int insertReply(ReviewReply r);

	int getSearchCount(@Param("keyword") String keyword);

	ArrayList<Review> searchReviews(@Param("keyword") String keyword, @Param("sort") String sort, RowBounds rowBounds);

	int reviewLike(HashMap<String, Object> map);

	int deleteLike(HashMap<String, Object> map);

	int insertLike(HashMap<String, Object> map);

	int likeCount(int pNumber);

	int deleteReview(int rNumber);

	int reviewUpdate(ReviewReply reply);

}