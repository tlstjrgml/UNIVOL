package com.univol.review.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.univol.common.PageInfo;
import com.univol.post.vo.Post;
import com.univol.review.vo.Review;
import com.univol.review.vo.ReviewReply;

@Mapper
public interface ReviewMapper {

	/* 목록 조회 */
	int getListCount(char c);
	ArrayList<Review> selectReviewList(@Param("pType") char c, @Param("sort") String sort, RowBounds rowBounds);

	/* 상세 조회 */
	Review selectReview(int bId);

	/* 등록 */
	int insertReview(Review r);

	/* 수정 */
	int updateReview(int bId);
	int updateView(int bId);
	int updateReviews(Review r);

	/* 삭제 */
	int deleteReview(int rNumber);

	/* 검색 */
	int getSearchCount(@Param("keyword") String keyword);
	ArrayList<Review> searchReviews(@Param("keyword") String keyword, @Param("sort") String sort, RowBounds rowBounds);
	int searchCount(String keyword);
	ArrayList<Review> searchReview(@Param("keyword") String keyword, @Param("pi") PageInfo pi);

	/* 메인페이지 TOP 5 */
	ArrayList<Review> selectTop();

	/* 댓글 */
	ArrayList<ReviewReply> selectReplyList(int bId);
	int insertReply(ReviewReply r);
	int reviewUpdate(ReviewReply reply);
	int reviewDelete(int cnum);

	/* 좋아요 */
	int reviewLike(HashMap<String, Object> map);
	int deleteLike(HashMap<String, Object> map);
	int insertLike(HashMap<String, Object> map);
	int likeCount(int pNumber);
	
	/* 참여봉사목록 */
	ArrayList<Post> selectApplyList(@Param("userId")String userId);
}