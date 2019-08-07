/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.identifyRecord.repository;

import com.aicheck.face.modules.identifyRecord.entity.IdentifyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 10:42 AM 2018/11/23
 */
public interface IdentifyRecordRepository extends JpaRepository<IdentifyRecord,Integer> {

    @Query(value = "select * from identify_record where create_time >= ?1 order by create_time desc",nativeQuery = true)
    List<IdentifyRecord> findByCreateTimeBefore(String startTime);


    @Query(value="select * from identify_record where customer_id = ?1 order by id desc limit 1", nativeQuery=true)
    IdentifyRecord findByLatest(String paramString);


    @Query(value = "select * from identify_record where create_time >= ?1 order by create_time desc limit 1",nativeQuery = true)
    IdentifyRecord findByLatestOrderByCreateTime(String date);

    @Query(value = "select * from (select face_coordinates as faceCoordinates from identify_record where create_time between ?1 and ?2 union all " +
            " select face_coordinates as faceCoordinates from visitors_record where create_time between ?1 and ?2) a where a.faceCoordinates is not null",nativeQuery = true)
    List<String> findByFaceCoordinates(String startTime, String endTime);

}
