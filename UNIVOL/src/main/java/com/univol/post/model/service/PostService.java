package com.univol.post.model.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.univol.post.model.Mapper.PostMapper;
import com.univol.post.model.vo.Post;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class PostService {
	
	private final PostMapper mapper;
	
	public ArrayList<Post> selectAll(int startRow, int endRow){
		return mapper.selectAll(startRow, endRow);
	}

	public int insertPost(Post p) {
		return mapper.insertPost(p);
	}
	
	public int getListCount() {
		return mapper.getListCount();
	}
}
