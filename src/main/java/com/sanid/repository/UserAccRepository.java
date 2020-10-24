package com.sanid.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanid.entity.StateEntity;
import com.sanid.entity.UserAccEntity;

public interface UserAccRepository extends JpaRepository<UserAccEntity, Serializable> {
	
	public UserAccEntity findByUserEmail(String email);
	
	public UserAccEntity findByUserEmailAndUserPazzword(String email, String tempPwd);
	
}
