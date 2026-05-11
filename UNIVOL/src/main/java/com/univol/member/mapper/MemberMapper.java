package com.univol.member.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.univol.member.vo.Member;

@Mapper	
public interface MemberMapper {

    Member logIn(Member m);

	void signUp(Member m);
	
    ArrayList<Member> selectAll();

	int updateMember(Member m);

	Member getMemberById(String userId);

	int deleteMember(Member m);
	
	int getApplyCount(String userId);
	
	int getMyPostCount(String userId);
	
	ArrayList<HashMap<String, Object>> getApplyList(String userId, RowBounds rowBounds);
	
	ArrayList<HashMap<String, Object>> getMyPostList(String userId, RowBounds rowBounds);

	int activeMember(Member m);

	int banMember(Member m);

	int toAdminMember(Member m);

	int toNormalMember(Member m);	

}