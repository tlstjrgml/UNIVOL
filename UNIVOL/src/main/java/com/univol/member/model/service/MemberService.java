package com.univol.member.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.univol.member.model.mapper.MemberMapper;
import com.univol.member.model.vo.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class MemberService {
	private final MemberMapper mapper;

	/* 로그인 */
	public Member logIn(Member m) {
		return mapper.logIn(m);
	}

	/* 회원가입 */
	public void signUp(Member m) {
		mapper.signUp(m);
	}
	
	public ArrayList<Member> selectAll() {
		return mapper.selectAll();
	}
	
	public ArrayList<HashMap<String, Object>> getApplyList(String id) {
		return mapper.getApplyList(id);
	}

	public int updateMember(Member m) {
		return mapper.updateMember(m);
	}
	

}
