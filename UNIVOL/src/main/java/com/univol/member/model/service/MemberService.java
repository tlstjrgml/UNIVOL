package com.univol.member.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.univol.member.model.Mapper.MemberMapper;
import com.univol.member.model.vo.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class MemberService {
	private final MemberMapper mapper;
	private final BCryptPasswordEncoder passwordEncoder;
	/* 로그인 */
	public Member logIn(Member m) {
		Member findMember = mapper.logIn(m);
		if(findMember != null) {
			if(passwordEncoder.matches(m.getUserPw(), findMember.getUserPw())) {
				return findMember;
			}else {
				return null;
			}
		}
		//return mapper.logIn(m);
		return null;
	}

	/* 회원가입 */
	public void signUp(Member m) {
		m.setUserPw(passwordEncoder.encode(m.getUserPw()));
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
