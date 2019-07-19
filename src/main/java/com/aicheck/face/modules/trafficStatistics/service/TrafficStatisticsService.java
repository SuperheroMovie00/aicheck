/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.trafficStatistics.service;


import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.trafficStatistics.entity.TrafficStatistics;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 7:31 PM 2018/12/26
 */
public interface TrafficStatisticsService extends BaseService<TrafficStatistics,Integer> {

    int[] getTrafficStatistics();
}
