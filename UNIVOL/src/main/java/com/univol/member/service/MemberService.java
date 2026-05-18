package com.univol.member.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.univol.common.PageInfo;
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
		if (findMember != null) {
			if (passwordEncoder.matches(m.getUserPw(), findMember.getUserPw())) {
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

	/* 회원 정보 */
	public Member getMemberById(String userId) {
		return mapper.getMemberById(userId);
	}

	public int updateMember(Member m) {
		return mapper.updateMember(m);
	}

	public int deleteMember(Member m) {
		return mapper.deleteMember(m);
	}

	

	

	/* 마이페이지 - 신청현황 */
	public int getApplyCount(String userId) {
		return mapper.getApplyCount(userId);
	}

	public ArrayList<HashMap<String, Object>> getApplyList(PageInfo pi, String userId) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.getApplyList(userId, rowBounds);
	}

	/* 마이페이지 - 작성글 */
	public int getMyPostCount(String userId) {
		return mapper.getMyPostCount(userId);
	}

	public ArrayList<HashMap<String, Object>> getMyPostList(PageInfo pi, String userId) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.getMyPostList(userId, rowBounds);
	}

	public int checkValue(HashMap<String, String> map) {
		return mapper.checkValue(map);
	}
}