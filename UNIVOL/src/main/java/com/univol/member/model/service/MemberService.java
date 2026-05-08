package com.univol.member.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.univol.member.model.mapper.MemberMapper;
import com.univol.member.model.vo.Member;
import com.univol.member.model.vo.PageInfo;

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
	

    public ArrayList<Member> selectAll() {
        return mapper.selectAll();
    }


    public int deleteMember(Member m) {
    	return mapper.deleteMember(m);
    }
    
    
    public int getApplyCount(String userId) {
        return mapper.getApplyCount(userId);
    }

    public int getMyPostCount(String userId) {
        return mapper.getMyPostCount(userId);
    }

    public ArrayList<HashMap<String, Object>> getApplyList(com.univol.post.model.vo.PageInfo pi, String userId) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return mapper.getApplyList(userId, rowBounds);
    }

    public ArrayList<HashMap<String, Object>> getMyPostList(com.univol.post.model.vo.PageInfo pi, String userId) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return mapper.getMyPostList(userId, rowBounds);
    }
}