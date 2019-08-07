/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.exceptionRecord.repository;

import com.aicheck.face.modules.exceptionRecord.entity.ExceptionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 11:56 AM 2019/1/24
 */
public interface ExceptionRecordRepository extends JpaRepository<ExceptionRecord,Integer> {
    @Transactional
    List<ExceptionRecord>  deleteByIdIn(List<Integer> ids);
}
