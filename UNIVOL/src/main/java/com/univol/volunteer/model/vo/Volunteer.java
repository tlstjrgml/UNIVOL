package com.univol.volunteer.model.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Volunteer {
	private int pNumber;
	private String vTitle;
	private Date vDate;
	private String pType;
	

}
