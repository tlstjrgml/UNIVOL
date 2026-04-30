package com.univol.post.model.Mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.univol.post.model.vo.Post;

@Mapper

public interface PostMapper {

	ArrayList<Post> selectAll();
}
