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

	public ArrayList<Post> selectAll(){
		return mapper.selectAll();
	}

<<<<<<< HEAD
	public Post selectOne(int pNumber) {
		return mapper.selectOne(pNumber);
=======
	public int insertPost(Post p) {
		return mapper.insertPost(p);
>>>>>>> branch '이효영' of https://github.com/tlstjrgml/UNIVOL.git
	}
}
