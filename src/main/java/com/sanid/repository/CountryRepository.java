package com.sanid.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanid.entity.CountryEntity;
import com.sanid.entity.UserAccEntity;

public interface CountryRepository extends JpaRepository<CountryEntity, Serializable> {

	//will find userAcc by select query for email check
		//that it is unique email or not
		public List<CountryEntity> findAll();
		
}
