package com.sanid.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "country_master")
public class CountryEntity {

	@Id
	@Column(name = "country_id")
	@GeneratedValue
	private Integer cid;
	@Column(name = "country_code")
	private String ccode;
	@Column(name = "country_name")
	private String cname;

}
