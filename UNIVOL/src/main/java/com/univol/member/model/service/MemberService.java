package com.univol.member.model.service;

import org.springframework.stereotype.Service;

import com.univol.member.model.Mapper.MemberMapper;
import com.univol.member.model.vo.Member;

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

	

}
