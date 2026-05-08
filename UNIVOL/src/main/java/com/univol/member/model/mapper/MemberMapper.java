package com.univol.member.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.univol.member.model.vo.Member;

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
}