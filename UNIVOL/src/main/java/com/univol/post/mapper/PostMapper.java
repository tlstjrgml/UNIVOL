package com.univol.post.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.univol.post.vo.Post;

@Mapper
public interface PostMapper {

	/* 게시글 목록 조회 */
	int getListCount();
	ArrayList<Post> selectAll(@Param("startRow") int startRow, @Param("endRow") int endRow, @Param("sort") String sort);

	/* 게시글 검색 */
	int getSearchCount(@Param("keyword") String keyword);
	ArrayList<Post> searchPosts(@Param("keyword") String keyword, @Param("sort") String sort, @Param("startRow") int startRow, @Param("endRow") int endRow);

	/* 게시글 상세 조회 */
	Post selectOne(@Param("pNumber") int pNumber, @Param("userId") String userId);

	/* 게시글 등록 */
	int insertPost(Post p);

	/* 게시글 수정/삭제 (작성자) */
	int userEditPost(Post p);
	int userDeletePost(int pNumber);

	/* 조회수/댓글수 업데이트 */
	int updateViews(int pNumber);
	int updateReview(int pNumber);

	/* 메인페이지 TOP 5 */
	ArrayList<Post> selectTopPost();

	/* 좋아요 */
	int postLike(HashMap<String, Object> map);
	void deleteLike(HashMap<String, Object> map);
	void insertLike(HashMap<String, Object> map);
	int likeCount(int pNumber);

	/* 관리자 - 게시글 목록 */
	int getPostCount();
	ArrayList<Post> selectAllPost();
	ArrayList<Post> selectAllPost(@Param("startRow") int startRow, @Param("endRow") int endRow);

	/* 관리자 - 게시글 상태 변경 */
	int rollbackPost(Post p);
	int deletePost(Post p);
}