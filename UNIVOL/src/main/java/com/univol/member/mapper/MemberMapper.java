package com.univol.member.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.univol.member.vo.Member;

@Mapper
public interface MemberMapper {

	/* 로그인/회원가입 */
	Member logIn(Member m);
	void signUp(Member m);

	/* 회원 정보 */
	Member getMemberById(String userId);
	int updateMember(Member m);
	int deleteMember(Member m);

	/* 마이페이지 */
	int getApplyCount(String userId);
	ArrayList<HashMap<String, Object>> getApplyList(String userId, RowBounds rowBounds);

	int getMyPostCount(String userId);
	ArrayList<HashMap<String, Object>> getMyPostList(String userId, RowBounds rowBounds);
	
	int checkValue(HashMap<String, String> map);
}