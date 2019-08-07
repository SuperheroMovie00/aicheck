/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.salesDetail.repository;

import com.aicheck.face.modules.salesDetail.entity.SalesDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:36 PM 2018/11/25
 */
public interface SalesDetailRepository extends JpaRepository<SalesDetail,Integer> {

    List<SalesDetail> findBySalesId(Integer salesId);
}
