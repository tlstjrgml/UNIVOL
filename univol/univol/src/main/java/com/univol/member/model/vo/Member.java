package com.univol.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member {

	private String userId;
	private String userPw;
	private String userName;
	private int age;
	private char gender;
	private String phone;
	private String address;
	private char isAdmin;
	
}
