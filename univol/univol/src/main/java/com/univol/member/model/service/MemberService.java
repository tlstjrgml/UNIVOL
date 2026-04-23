package com.univol.member.model.service;

import org.springframework.stereotype.Service;

import com.univol.member.model.mapper.MemberMapper;
import com.univol.member.model.vo.Member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class MemberService {
	private final MemberMapper mapper;

	public Member logIn(Member m) {
		return mapper.logIn(m);
	}

	public void signUp(Member m) {
		mapper.signUp(m);
	}

	public void myPage(Member m, HttpSession session) {
		mapper.myPage(m, session);
	}
	
	


}
