package com.univol.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.univol.vo.MemberVO;

@Mapper
public interface MemberMapper {
	MemberVO logIn(String UserId);
}
