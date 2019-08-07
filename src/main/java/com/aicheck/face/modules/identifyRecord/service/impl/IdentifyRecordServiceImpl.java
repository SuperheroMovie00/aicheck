/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.identifyRecord.service.impl;

import com.aicheck.face.modules.identifyRecord.entity.IdentifyRecord;
import com.aicheck.face.modules.identifyRecord.repository.IdentifyRecordRepository;
import com.aicheck.face.modules.identifyRecord.service.IdentifyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 10:41 AM 2018/11/23
 */
@Service("identifyRecordService")
public class IdentifyRecordServiceImpl implements IdentifyRecordService {
    @Autowired
    private IdentifyRecordRepository identifyRecordRepository;
    @Override
    public synchronized IdentifyRecord save(IdentifyRecord identifyRecord) {

        IdentifyRecord record = this.identifyRecordRepository.findByLatest(identifyRecord.getCustomerId());
        if (record != null) {
            long timestamp = record.getCreateTime().getTime();
            long currentTimestamp = new Date().getTime();
            long diff = currentTimestamp - timestamp;

            long minute = diff / 1000L / 60L;

            if (minute < 720L) {
                record.setCreateTime(new Date());
                return identifyRecordRepository.save(record);
            }
        }

        return identifyRecordRepository.save(identifyRecord);
    }

    @Override
    public List<IdentifyRecord> save(List<IdentifyRecord> t) {
        return identifyRecordRepository.saveAll(t);
    }

    @Override
    public void delete(Integer integer) {
        identifyRecordRepository.deleteById(integer);
    }

    @Override
    public IdentifyRecord update(IdentifyRecord identifyRecord) {

        return identifyRecordRepository.save(identifyRecord);
    }

    @Override
    public List<IdentifyRecord> find(IdentifyRecord identifyRecord) {
        return null;
    }

    @Override
    public List<IdentifyRecord> findAll() {
        return identifyRecordRepository.findAll();
    }

    @Override
    public IdentifyRecord findById(Integer integer) {
        Optional<IdentifyRecord> optionalIdentifyRecord = identifyRecordRepository.findById(integer);

        return optionalIdentifyRecord.isPresent()?optionalIdentifyRecord.get():null;
    }

    @Override
    public Page<IdentifyRecord> findAllList(Integer currentPage, Integer pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1,pageSize);

        return identifyRecordRepository.findAll(pageable);
    }

    @Override
    public List<IdentifyRecord> findByCreateTimeBefore(Date startTime) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return identifyRecordRepository.findByCreateTimeBefore(simpleDateFormat.format(startTime));
    }

    @Override
    public Page<IdentifyRecord> findByNotifyList(int pageSize) {

        Pageable pageable = PageRequest.of(0,pageSize,Sort.Direction.DESC,"createTime");

        return identifyRecordRepository.findAll(pageable);
    }

    @Override
    public IdentifyRecord findByLatestOrderByCreateTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return identifyRecordRepository.findByLatestOrderByCreateTime(simpleDateFormat.format(date));
    }

    @Override
    public List<String> findByFaceCoordinates(String startTime, String endTime) {

        if (!StringUtils.isEmpty(startTime) && startTime.length() ==10) {

            startTime += " 00:00:00";

        }

        if (!StringUtils.isEmpty(endTime) && endTime.length() == 10) {

            endTime += " 23:59:59";

        }


        return identifyRecordRepository.findByFaceCoordinates(startTime,endTime);
    }
}
