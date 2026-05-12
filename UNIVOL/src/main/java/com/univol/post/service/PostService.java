package com.univol.post.service;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	public int userDeletePost(int pNumber) {
		return mapper.userDeletePost(pNumber);
	}

	public int postLike(int pNumber, String userId) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("pNumber", pNumber);
		map.put("userId", userId);
		return mapper.postLike(map);
	}

	public void deleteLike(int pNumber, String userId) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("pNumber", pNumber);
		map.put("userId", userId);
		mapper.deleteLike(map);
	}

	public void insertLike(int pNumber, String userId) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("pNumber", pNumber);
		map.put("userId", userId);
		mapper.insertLike(map);
	}

	public int likeCount(int pNumber) {
		HashMap<String, Object> map = new HashMap<>();
		return mapper.likeCount(pNumber);
	}
}