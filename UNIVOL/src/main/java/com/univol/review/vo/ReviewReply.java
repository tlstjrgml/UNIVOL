package com.univol.review.vo;

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
public class ReviewReply {
	private int cNumber;
	private String comments;
	private Date cDate;
	private int pNumber;
	private String userId;
	private String userName;
	private String cStatus;
}
