package com.univol.admin;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.univol.member.vo.Member;
import com.univol.post.vo.Post;
import com.univol.post.vo.Reply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {

	/* 관리자 - 회원 목록 */
	int getMemberCount();
	ArrayList<Member> selectAll(@Param("startRow") int startRow, @Param("endRow") int endRow);

	/* 관리자 - 회원 상태/권한 변경 */
	int activeMember(Member m);
	int banMember(Member m);
	int toAdminMember(Member m);
	int toNormalMember(Member m);
	
	/* 관리자 - 게시글 목록 */
	int getPostCount();
	ArrayList<Post> selectAllPost();
	ArrayList<Post> selectAllPost(@Param("startRow") int startRow, @Param("endRow") int endRow);

	/* 관리자 - 게시글 상태 변경 */
	int rollbackPost(Post p);
	int deletePost(Post p);
	
	int adminDeleteReply(@Param("rNumber")int rNumber);
	
	int adminRollbackReply(@Param("rNumber")int rNumber);

	int getReplyCount();
	
	ArrayList<Reply> selectAllReply(@Param("startRow")int replyStartRow, @Param("endRow")int replyEndRow);
	ArrayList<Reply> selectAllReply();
}
