/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.salesRecord.service;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.salesRecord.entity.SalesRecord;
import org.springframework.data.domain.Page;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:39 PM 2018/11/21
 */
public interface SalesRecordService extends BaseService<SalesRecord,Integer> {

    Page<SalesRecord> findAll(Integer currentPage, Integer pageSize);

    SalesRecord findByCustomerIdLatest(Integer customerId);

    Page<SalesRecord> findByCustomerId(Integer customerId, Integer currentPage, Integer pageSize);
}
