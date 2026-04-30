package com.univol.post.model.vo;

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


	private String userName;
	private char pStatus;
	private int likes;

<<<<<<< HEAD
	private String userId;

=======

	private String userId;



	private char pType; /* commit test */ /* pushTest */




>>>>>>> origin/신석희
}



