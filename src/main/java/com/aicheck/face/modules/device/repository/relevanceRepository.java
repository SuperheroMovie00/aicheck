package com.aicheck.face.modules.device.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.aicheck.face.modules.device.entity.relevance;

@SuppressWarnings("rawtypes")
public interface relevanceRepository extends JpaRepository<relevance,Integer>,JpaSpecificationExecutor {
	
	@Query(value ="select *from relevance where boxdevice_id=?1",nativeQuery = true)
	relevance queryrelevanceforboxdeviceid(Integer id);
    
}
