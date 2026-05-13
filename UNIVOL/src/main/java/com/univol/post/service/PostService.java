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

	/* 게시글 목록 조회 */
	public int getListCount() {
		return mapper.getListCount();
	}

	public ArrayList<Post> selectAll(int startRow, int endRow, String sort) {
		return mapper.selectAll(startRow, endRow, sort);
	}

	/* 게시글 검색 */
	public int getSearchCount(String keyword) {
		return mapper.getSearchCount(keyword);
	}

	public ArrayList<Post> searchPosts(String keyword, String sort, int startRow, int endRow) {
		return mapper.searchPosts(keyword, sort, startRow, endRow);
	}

	/* 게시글 상세 조회 */
	public Post selectOne(int pNumber, String userId) {
		Post post = mapper.selectOne(pNumber, userId);
		if (post != null) {
			if (userId != null && !post.getUserId().equals(userId)) {
				int result = mapper.updateReview(pNumber);
				if (result > 0) {
					post.setViews(post.getViews() + 1);
				}
			}
		}
		return post;
	}

	/* 게시글 등록 */
	public int insertPost(Post p) {
		return mapper.insertPost(p);
	}

	/* 게시글 수정/삭제 (작성자) */
	public int userEditPost(Post p) {
		return mapper.userEditPost(p);
	}

	public int userDeletePost(int pNumber) {
		return mapper.userDeletePost(pNumber);
	}

	/* 조회수/댓글수 업데이트 */
	public int updateReview(int pNumber) {
		return mapper.updateReview(pNumber);
	}

	/* 메인페이지 TOP 5 */
	public ArrayList<Post> selectTopPost() {
		return mapper.selectTopPost();
	}

	/* 관리자 - 게시글 목록 */
	public int getPostCount() {
		return mapper.getPostCount();
	}

	public ArrayList<Post> selectAllPost() {
		return mapper.selectAllPost();
	}

	public ArrayList<Post> selectAllPost(int startRow, int endRow) {
		return mapper.selectAllPost(startRow, endRow);
	}

	/* 관리자 - 게시글 상태 변경 */
	public int deletePost(Post p) {
	    return mapper.deletePost(p);
	}

	public int rollbackPost(Post p) {
	    return mapper.rollbackPost(p);
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