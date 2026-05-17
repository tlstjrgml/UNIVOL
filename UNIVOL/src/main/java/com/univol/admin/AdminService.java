package com.univol.admin;

import java.util.ArrayList;

import com.univol.member.vo.Member;
import com.univol.post.vo.Post;
import com.univol.post.vo.Reply;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final AdminMapper mapper;
	/* 관리자 - 회원 목록 */
	public int getMemberCount() {
		return mapper.getMemberCount();
	}

	public ArrayList<Member> selectAll(int startRow, int endRow) {
		return mapper.selectAll(startRow, endRow);
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
	
	public ArrayList<Reply> selectAllReply(){
		return mapper.selectAllReply();
	}
	
	public int adminDeleteReply(int rNumber) {
		return mapper.adminDeleteReply(rNumber);
	}

	public int adminRollbackReply(int rNumber) {
		return mapper.adminRollbackReply(rNumber);
	}

	public ArrayList<Reply> selectAllReply(int replyStartRow, int replyEndRow) {
		return mapper.selectAllReply(replyStartRow, replyEndRow);
	}
	
	public int activeMember(Member m) {
		return mapper.activeMember(m);
	}
	
	public int banMember(Member m) {
		return mapper.banMember(m);
	}
	
	public int toAdminMember(Member m) {
		return mapper.toAdminMember(m);
	}
	
	public int toNormalMember(Member m) {
		return mapper.toNormalMember(m);
	}
	
	public int getReplyCount() {
		return mapper.getReplyCount();
	}

	
}
