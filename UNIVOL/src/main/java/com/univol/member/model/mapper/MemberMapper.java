package com.univol.member.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.univol.member.model.vo.Member;

import jakarta.servlet.http.HttpSession;

@Mapper

public interface MemberMapper {

    Member logIn(Member m);

	void signUp(Member m);
	
	// 주석 테스트ppppp

    ArrayList<Member> selectAll();

	ArrayList<HashMap<String, Object>> getApplyList(String id);

	int updateMember(Member m);

	int deleteMember(Member m);

	int activeMember(Member m);

	int banMember(Member m);

	int toAdminMember(Member m);

	int toNormalMember(Member m);	


}