package com.univol.post.model.Mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.univol.post.model.vo.Post;

@Mapper

public interface PostMapper {

	ArrayList<Post> selectAll();

<<<<<<< HEAD
	Post selectOne(int pNumber);
=======
	int insertPost(Post p);
>>>>>>> branch '이효영' of https://github.com/tlstjrgml/UNIVOL.git
}
