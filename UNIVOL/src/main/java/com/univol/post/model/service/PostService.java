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
	
	public ArrayList<Post> selectAll(int startRow, int endRow, String sort){
		return mapper.selectAll(startRow, endRow, sort);
	}


	public Post selectOne(int pNumber, String userId) {
		Post p = mapper.selectOne(pNumber);
		if(p != null) {
			//당사자 게시글이면 조회수 그대로, 아니면 조회수 증가.
			if(userId != null && !p.getUserId().equals(userId)) {
				int result = mapper.updateViews(pNumber);
				if(result > 0) {
					p.setViews(p.getViews() + 1);
				}
			}
		}
		return p;
	}

	public int insertPost(Post p) {
		return mapper.insertPost(p);
	}
	
	public int getListCount() {
		return mapper.getListCount();
	}


	public int getSearchCount(String keyword) {
		return mapper.getSearchCount(keyword);
	}


	public ArrayList<Post> searchPosts(String keyword, String sort, int startRow, int endRow) {
		return mapper.searchPosts(keyword, sort, startRow, endRow);
	}
	
	public ArrayList<Post> selectAllPost(){
		return mapper.selectAllPost();
	}
	
	public int deletePost(Post p) {
		return mapper.deletePost(p);
	}
	
	public int rollbackPost(Post p) {
		return mapper.rollbackPost(p);
	}
}
