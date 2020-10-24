package com.sanid.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanid.entity.CityEntity;

public interface CityRepository extends JpaRepository<CityEntity, Serializable> {

	public List<CityEntity> findBysid(Integer stateId);
	
}
