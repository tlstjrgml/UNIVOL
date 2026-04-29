package com.univol.member.model.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.univol.member.model.vo.Member;

@Mapper

public interface MemberMapper {

    Member logIn(Member m);

	void signUp(Member m);
	
	// 주석 테스트ppppp

    ArrayList<Member> selectAll();

	ArrayList<HashMap<String, Object>> getApplyList(String id);
}