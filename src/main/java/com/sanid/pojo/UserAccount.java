package com.sanid.pojo;

import java.sql.Date;

import lombok.Data;

@Data
public class UserAccount {
	
	private Integer uid;
	private String fname;
	private String lname;
	private String userEmail;
	private String phno;
	private String dob;
	private String gender;
	private Integer countryId;
	private Integer stateId;
	private String updated_date;
	private Integer cityId;
	private String accStatus;
	private String userPazzword;
}