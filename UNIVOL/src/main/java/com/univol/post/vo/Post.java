package com.univol.post.vo;

import java.sql.Date;

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
public class Post {
	private int pNumber;
	private String pTitle;
	private String contents;
	private int views;
	private Date pDate;
	private char pType;
	private String userId;
	private String userName;
	private char pStatus;
	private int likes;
	private String isNotice;
	private Date createDate;
	private int likeCount;
	
	
}
