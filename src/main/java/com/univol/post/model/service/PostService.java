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
}
