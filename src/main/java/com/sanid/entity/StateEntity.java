package com.sanid.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "states_master")
public class StateEntity {

	@Id
	@Column(name = "state_id")
	@GeneratedValue
	private Integer sid;
	@Column(name = "country_id")
	private Integer cid;
	@Column(name = "state_name")
	private String sname;
}
