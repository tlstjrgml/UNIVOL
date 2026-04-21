package com.univol.vo;

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

public class MemberVO {
    private String userId;
    private String userPw;
    private String userName;
    private int age;
    private String gender;
    private String phone;
    private String address;
    private String isAdmin;
}
