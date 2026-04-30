package com.univol.review.model.vo;

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
public class Review {
	private int rNumber;
	private String rTitle;
	private String rContents;
	private String rViews;
	private Date rDate;
	private String pType;
	private String userId;
	private String rStatus;
	private String userName;
}
