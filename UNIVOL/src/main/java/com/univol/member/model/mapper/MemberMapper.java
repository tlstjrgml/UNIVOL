package com.univol.member.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.univol.member.model.vo.Member;

@Mapper

public interface MemberMapper {

    Member logIn(Member m);

	void signUp(Member m);
	
    ArrayList<Member> selectAll();

	ArrayList<HashMap<String, Object>> getApplyList(String id);

	int updateMember(Member m);

	Member getMemberById(String userId);
}