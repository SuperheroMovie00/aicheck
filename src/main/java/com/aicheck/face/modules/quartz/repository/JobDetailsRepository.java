/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.quartz.repository;

import com.aicheck.face.modules.quartz.entity.JobDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 4:07 PM 2019/4/9
 */
public interface JobDetailsRepository extends JpaRepository<JobDetails,String> {

    @Query(value = "SELECT jd.JOB_NAME AS jobName, jd.DESCRIPTION AS jobDescription, jd.JOB_GROUP AS jobGroupName, jd.JOB_CLASS_NAME AS jobClassName, " +
            "t.TRIGGER_NAME AS triggerName, t.TRIGGER_GROUP AS triggerGroupName, FROM_UNIXTIME(t.PREV_FIRE_TIME/1000,'%Y-%m-%d %T') AS prevFireTime, FROM_UNIXTIME(t.NEXT_FIRE_TIME/1000,'%Y-%m-%d %T') AS nextFireTime, " +
            "ct.CRON_EXPRESSION AS cronExpression, t.TRIGGER_STATE AS triggerState FROM QRTZ_JOB_DETAILS jd JOIN QRTZ_TRIGGERS t JOIN QRTZ_CRON_TRIGGERS ct ON jd.JOB_NAME = t.JOB_NAME AND t.TRIGGER_NAME = ct.TRIGGER_NAME AND t.TRIGGER_GROUP = ct.TRIGGER_GROUP",nativeQuery = true)
    List<JobDetails> findByAll();

    @Query(value = "select CRON_EXPRESSION from QRTZ_CRON_TRIGGERS",nativeQuery = true)
    String findByCron();
}
