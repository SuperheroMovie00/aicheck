/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.exceptionRecord.service;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.exceptionRecord.entity.ExceptionRecord;
import org.springframework.data.domain.Page;


/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 11:58 AM 2019/1/24
 */
public interface ExceptionRecordService extends BaseService<ExceptionRecord,Integer> {

    Page<ExceptionRecord> findAll(Integer currentPage,Integer pageSize);

    void delete(Integer[] ids);
}
