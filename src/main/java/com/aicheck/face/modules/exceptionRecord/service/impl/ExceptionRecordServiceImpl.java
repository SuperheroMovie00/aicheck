/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.exceptionRecord.service.impl;

import com.aicheck.face.modules.exceptionRecord.entity.ExceptionRecord;
import com.aicheck.face.modules.exceptionRecord.repository.ExceptionRecordRepository;
import com.aicheck.face.modules.exceptionRecord.service.ExceptionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 1:35 PM 2019/1/24
 */
@Service("exceptionRecordService")
public class ExceptionRecordServiceImpl implements ExceptionRecordService {
    @Autowired
    private ExceptionRecordRepository exceptionRecordRepository;
    @Override
    public ExceptionRecord save(ExceptionRecord exceptionRecord) {
        return exceptionRecordRepository.save(exceptionRecord);
    }

    @Override
    public List<ExceptionRecord> save(List<ExceptionRecord> t) {
        return exceptionRecordRepository.saveAll(t);
    }

    @Override
    public void delete(Integer integer) {
        exceptionRecordRepository.deleteById(integer);
    }

    @Override
    public void delete(Integer[] ids) {
        exceptionRecordRepository.deleteByIdIn(Arrays.asList(ids));
    }

    @Override
    public ExceptionRecord update(ExceptionRecord exceptionRecord) {
        return exceptionRecordRepository.save(exceptionRecord);
    }

    @Override
    public List<ExceptionRecord> find(ExceptionRecord exceptionRecord) {
        return null;
    }

    @Override
    public List<ExceptionRecord> findAll() {
        return exceptionRecordRepository.findAll();
    }

    @Override
    public ExceptionRecord findById(Integer integer) {
        Optional<ExceptionRecord> optional = exceptionRecordRepository.findById(integer);
        return optional.isPresent()?optional.get():null;
    }

    @Override
    public Page<ExceptionRecord> findAll(Integer currentPage, Integer pageSize) {

        Pageable pageable = PageRequest.of(currentPage -1,pageSize,Sort.Direction.DESC,"createTime");

        Page<ExceptionRecord> page = exceptionRecordRepository.findAll(pageable);

        return page;
    }
}
