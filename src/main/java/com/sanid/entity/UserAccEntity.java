package com.sanid.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "user_master")
public class UserAccEntity {

	@Id
	@Column(name = "user_id")
	@GeneratedValue
	private Integer uid;
	@Column(name = "first_name")
	private String fname;
	@Column(name = "last_name")
	private String lname;
	@Column(name = "userEmail")
	private String userEmail;
	@Column(name = "ph_no")
	private String phno;
	@Column(name = "dob")
	private String dob;
	@Column(name = "gender")
	private String gender;
	@Column(name = "countryId")
	private Integer countryId;
	@Column(name = "stateId")
	private Integer stateId;
	@Column(name = "updated_date")
	private String updated_date;
	@Column(name = "cityId")
	private Integer cityId;
	
	@Column(name = "AccStatus")
	private String AccStatus;
	@Column(name = "UserPazzword")
	private String userPazzword;

}
