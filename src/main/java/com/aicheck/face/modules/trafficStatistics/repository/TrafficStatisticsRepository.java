/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.trafficStatistics.repository;


import com.aicheck.face.modules.trafficStatistics.entity.TrafficStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 7:30 PM 2018/12/26
 */
public interface TrafficStatisticsRepository extends JpaRepository<TrafficStatistics,Integer> {

}
