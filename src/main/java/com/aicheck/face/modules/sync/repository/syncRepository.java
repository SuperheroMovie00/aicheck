package com.aicheck.face.modules.sync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aicheck.face.modules.sync.entity.sync;

public interface syncRepository extends JpaRepository<sync,Integer> {

	
	@Query(value = "select *from sync where status=?1 and func=?2",nativeQuery = true)
	List<sync> querysynclist(Integer status,String func);
	
}
