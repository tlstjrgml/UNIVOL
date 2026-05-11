package com.univol.post.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.univol.post.mapper.PostMapper;
import com.univol.post.vo.Post;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostMapper mapper;

	public ArrayList<Post> selectAll(int startRow, int endRow, String sort){
		return mapper.selectAll(startRow, endRow, sort);
	}

	public Post selectOne(int pNumber, String userId) {
	    Post post = mapper.selectOne(pNumber, userId);
	    if(post != null) {
	        if(userId != null && !post.getUserId().equals(userId)) {
	            int result = mapper.updateReview(pNumber);
	            if(result > 0) {
	                post.setViews(post.getViews() + 1);
	            }
	        }
	    }
	    return post;
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

	public int getPostCount() {
		return mapper.getPostCount();
	}

	public ArrayList<Post> selectAllPost(int startRow, int endRow){
		return mapper.selectAllPost(startRow, endRow);
	}

	public int updateReview(int pNumber) {
		return mapper.updateReview(pNumber);
	}

	public ArrayList<Post> selectTopPost() {
		return mapper.selectTopPost();
	}
}