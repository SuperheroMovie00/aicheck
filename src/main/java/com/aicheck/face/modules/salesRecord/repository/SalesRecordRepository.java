/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.salesRecord.repository;

import com.aicheck.face.modules.salesRecord.entity.SalesRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:39 PM 2018/11/21
 */
public interface SalesRecordRepository extends JpaRepository<SalesRecord,Integer> {
    @Query(value = "select * from sales_record where customer_id = ?1 order by id desc limit 1 ",nativeQuery = true)
    SalesRecord findByCustomerIdLatest(Integer customerId);

    Page<SalesRecord> findByCustomerId(Integer customerId, Pageable pageable);
}
