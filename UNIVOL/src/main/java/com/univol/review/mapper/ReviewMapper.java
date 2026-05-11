package com.univol.review.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.univol.review.vo.ReviewReply;
import com.univol.review.vo.Review;

@Mapper
public interface ReviewMapper {

	int getListCount(char c);

	ArrayList<Review> selectReviewList(char c, RowBounds rowBounds);

	Review selectReview(int bId);

<<<<<<< HEAD
	int updateReview(int bId);
 
=======
	int updateView(int bId);

>>>>>>> b365c3c56fc67378f98c1bd4481f3c37d75f8d86
	int updateReviews(Review r);

	int insertReview(Review r);

	ArrayList<Review> selectTop();

	ArrayList<com.univol.review.vo.ReviewReply> selectReplyList(int bId);

	int insertReply(com.univol.review.vo.ReviewReply r);
	

}
