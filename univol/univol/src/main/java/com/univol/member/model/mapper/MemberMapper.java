package com.univol.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.univol.member.model.vo.Member;

import jakarta.servlet.http.HttpSession;

@Mapper

public interface MemberMapper {

	Member logIn(Member m);

	void signUp(Member m);

	void myPage(Member m, HttpSession session);	
	
}
