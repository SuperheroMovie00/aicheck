/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.visitorsRecord.service;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.visitorsRecord.entity.VisitorsRecord;

import java.util.Date;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 7:30 PM 2018/11/27
 */
public interface VisitorsRecordService extends BaseService<VisitorsRecord,Integer> {

    VisitorsRecord findLatestOrderByCreateTime(Date date);
    
    
    //统计前处理（去除重复，在若干小时内进入的同一个用户的信息算做一条）
    void disposition();
    
    
    
    
    

}
