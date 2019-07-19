/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.salesRecord.service.impl;

import com.aicheck.face.modules.salesRecord.entity.SalesRecord;
import com.aicheck.face.modules.salesRecord.repository.SalesRecordRepository;
import com.aicheck.face.modules.salesRecord.service.SalesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:40 PM 2018/11/21
 */
@Service("salesRecordService")
public class SalesRecordServiceImpl implements SalesRecordService {

    @Autowired
    private SalesRecordRepository salesRecordRepository;

    @Override
    public SalesRecord save(SalesRecord salesRecord) {
        salesRecord.setCreateId(1);
        return salesRecordRepository.save(salesRecord);
    }

    @Override
    public List<SalesRecord> save(List<SalesRecord> t) {
        t.forEach(salesRecord -> {
            salesRecord.setCreateId(1);
        });
        return salesRecordRepository.saveAll(t);
    }

    @Override
    public void delete(Integer integer) {
        salesRecordRepository.deleteById(integer);
    }

    @Override
    public SalesRecord update(SalesRecord salesRecord) {
        return salesRecordRepository.save(salesRecord);
    }

    @Override
    public List<SalesRecord> find(SalesRecord salesRecord) {
        return null;
    }

    @Override
    public List<SalesRecord> findAll() {
        return salesRecordRepository.findAll();
    }

    @Override
    public SalesRecord findById(Integer integer) {
        Optional<SalesRecord> optionalSalesRecord = salesRecordRepository.findById(integer);
        return optionalSalesRecord.isPresent()?optionalSalesRecord.get():null;
    }

    @Override
    public Page<SalesRecord> findAll(Integer currentPage, Integer pageSize) {
        Pageable pageable = PageRequest.of(currentPage -1,pageSize,Sort.Direction.DESC,"createTime");
        return salesRecordRepository.findAll(pageable);
    }

    @Override
    public SalesRecord findByCustomerIdLatest(Integer customerId) {

        return salesRecordRepository.findByCustomerIdLatest(customerId);
    }

    @Override
    public Page<SalesRecord> findByCustomerId(Integer customerId, Integer currentPage, Integer pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1,pageSize);
        return salesRecordRepository.findByCustomerId(customerId,pageable);
    }
}
