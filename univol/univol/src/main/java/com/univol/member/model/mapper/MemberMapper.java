package com.univol.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.univol.member.model.vo.Member;

@Mapper

public interface MemberMapper {

	Member logIn(Member m);
	
}
