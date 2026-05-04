package com.univol.post.model.Mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.univol.post.model.vo.Post;

@Mapper

public interface PostMapper {

	ArrayList<Post> selectAll(@Param("startRow") int startRow, @Param("endRow")int endRow);

	Post selectOne(int pNumber);

	int insertPost(Post p);
	
	int getListCount();
	

}
