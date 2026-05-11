package com.univol.post.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.univol.post.vo.Post;

@Mapper

public interface PostMapper {

	ArrayList<Post> selectAll(@Param("startRow") int startRow, @Param("endRow")int endRow, @Param("sort")String sort);

	Post selectOne(@Param("pNumber") int pNumber, @Param("userId") String userId);

	int insertPost(Post p);
	
	int getListCount();

	int getSearchCount(@Param("keyword")String keyword);

	ArrayList<Post> searchPosts(@Param("keyword")String keyword, @Param("sort")String sort, @Param("startRow") int startRow, @Param("endRow")int endRow);
	
	ArrayList<Post> selectAllPost();
	
	int rollbackPost(Post p);
	
	int deletePost(Post p);
	
	int getPostCount();
	
	ArrayList<Post> selectAllPost(@Param("startRow") int startRow, @Param("endRow") int endRow);

	int updateReview(int pNumber);

	ArrayList<Post> selectTopPost();

}
