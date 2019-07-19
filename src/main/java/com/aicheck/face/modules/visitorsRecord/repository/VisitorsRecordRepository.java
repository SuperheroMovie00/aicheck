/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.visitorsRecord.repository;

import com.aicheck.face.modules.visitorsRecord.entity.VisitorsRecord;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 7:30 PM 2018/11/27
 */
public interface VisitorsRecordRepository extends JpaRepository<VisitorsRecord, Integer> {

	@Query(value = "select * from visitors_record where create_time >= ?1 order by create_time desc limit 1", nativeQuery = true)
	VisitorsRecord findLatestOrderByCreateTime(String date);

	@Query(value = "select * from visitors_record where customer_id = ?1 order by id desc limit 1", nativeQuery = true)
	VisitorsRecord findLatestByCustomerId(String customerId);
	
	@Query(value = "select * from visitors_record where process_dup=0  order by customer_id ,create_time desc ", nativeQuery = true)
	List<VisitorsRecord> queryvisitorsfromprocess();
	

}
