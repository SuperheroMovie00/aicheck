/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.salesDetail.service.impl;

import com.aicheck.face.modules.salesDetail.entity.SalesDetail;
import com.aicheck.face.modules.salesDetail.repository.SalesDetailRepository;
import com.aicheck.face.modules.salesDetail.service.SalesDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:36 PM 2018/11/25
 */
@Service("salesDetailService")
public class SalesDetailServiceImpl implements SalesDetailService {
    @Autowired
    private SalesDetailRepository salesDetailRepository;

    @Override
    public SalesDetail save(SalesDetail salesDetail) {
        return salesDetailRepository.save(salesDetail);
    }

    @Override
    public List<SalesDetail> save(List<SalesDetail> t) {
        return salesDetailRepository.saveAll(t);
    }

    @Override
    public void delete(Integer integer) {
        salesDetailRepository.deleteById(integer);
    }

    @Override
    public SalesDetail update(SalesDetail salesDetail) {
        return salesDetailRepository.save(salesDetail);
    }

    @Override
    public List<SalesDetail> find(SalesDetail salesDetail) {
        return null;
    }

    @Override
    public List<SalesDetail> findAll() {
        return salesDetailRepository.findAll();
    }

    @Override
    public SalesDetail findById(Integer integer) {
        Optional<SalesDetail> optionalSalesDetail = salesDetailRepository.findById(integer);

        return optionalSalesDetail.isPresent()?optionalSalesDetail.get():null;
    }

    @Override
    public List<SalesDetail> findBySalesId(Integer salesId) {
        return salesDetailRepository.findBySalesId(salesId);
    }
}
