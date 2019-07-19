/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.identifyRecord.service;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.identifyRecord.entity.IdentifyRecord;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 10:40 AM 2018/11/23
 */
public interface IdentifyRecordService extends BaseService<IdentifyRecord,Integer> {

    Page<IdentifyRecord> findAllList(Integer currentPage, Integer pageSize);

    List<IdentifyRecord> findByCreateTimeBefore(Date startTime);

    Page<IdentifyRecord> findByNotifyList(int pageSize);

    IdentifyRecord findByLatestOrderByCreateTime(Date date);

    List<String> findByFaceCoordinates(String startTime, String endTime);
}
