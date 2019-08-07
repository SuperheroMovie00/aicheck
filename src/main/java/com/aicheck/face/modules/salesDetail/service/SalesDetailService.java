/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.salesDetail.service;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.salesDetail.entity.SalesDetail;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:29 PM 2018/11/25
 */
public interface SalesDetailService extends BaseService<SalesDetail,Integer> {

    List<SalesDetail> findBySalesId(Integer salesId);
}
