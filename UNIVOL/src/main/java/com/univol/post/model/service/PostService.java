package com.univol.post.model.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.univol.post.model.Mapper.PostMapper;
import com.univol.post.model.vo.Post;
import com.univol.review.model.vo.Review;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class PostService {

	private final PostMapper mapper;
	
	public ArrayList<Post> selectAll(int startRow, int endRow){
		return mapper.selectAll(startRow, endRow);
	}


	public Post selectOne(int pNumber) {
		return mapper.selectOne(pNumber);
	}

	public int insertPost(Post p) {
		return mapper.insertPost(p);
	}
	
	public int getListCount() {
		return mapper.getListCount();
	}


	public Post selectOne(int pNumber, String id) {
		Post p = mapper.selectOne(pNumber);
		if(p != null) {
			if(id != null && !p.getUserId().equals(id)) {
				int result = mapper.updateReview(pNumber);
				if(result > 0) {
					p.setViews(p.getViews() + 1);
				}
			}
		}
		return p;
	}


}
