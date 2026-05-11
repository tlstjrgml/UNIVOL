package com.univol.member.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.univol.member.mapper.MemberMapper;
import com.univol.member.vo.Member;

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
			}
		}
		return null;
	}

	
	/* 회원가입 */
	public void signUp(Member m) {
		m.setUserPw(passwordEncoder.encode(m.getUserPw()));
		mapper.signUp(m);
	}


	public int updateMember(Member m) {
		return mapper.updateMember(m);
	}
	public Member getMemberById(String userId) {
		return mapper.getMemberById(userId);
	}
	

    public ArrayList<Member> selectAll(int startRow, int endRow) {
        return mapper.selectAll(startRow, endRow);
    }

    public ArrayList<HashMap<String, Object>> getApplyList(String id) {
        return mapper.getApplyList(id);
    }

    public int deleteMember(Member m) {
    	return mapper.deleteMember(m);
    }


	public int activeMember(Member m) {
		return mapper.activeMember(m);
	}


	public int banMember(Member m) {
		// TODO Auto-generated method stub
		return mapper.banMember(m);
	}


	public int toAdminMember(Member m) {
		return mapper.toAdminMember(m);
	}


	public int toNormalMember(Member m) {
		return mapper.toNormalMember(m);
	}
   
	/* 관리자페이지 회원 페이지네이션 */
	public int getMemberCount() {
		return mapper.getMemberCount();
	}

}