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
<<<<<<< HEAD
	private char pType;

=======
	private char pType; /* commit test */ /* pushTest */
>>>>>>> b418efc44aab4e7549f6903085c3940deb2c8e2b
}



