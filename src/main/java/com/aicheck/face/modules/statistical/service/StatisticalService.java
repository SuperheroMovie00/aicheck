/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.statistical.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 11:03 AM 2018/11/26
 */
public interface StatisticalService {



    Map<String,Object> statisticalInit(Date date);

    Map<String,Object> statisticalGender();

    Object[] statisticalAge();

    List<Map<String,Object>> statisticalTeam(Date date, Integer tagId);


    List<Map<String,Object>> statisticalOldPassengerFlowByDay(Date startTime, Date endTime);

    List<Map<String,Object>> statisticalOldPassengerFlowByWeek(Date startTime, Date endTime);

    List<Map<String,Object>> statisticalOldPassengerFlowByMonth(Date startTime, Date endTime);

    List<Map<String,Object>> statisticalOldPassengerFlowByMonthCustomer(Date startTime, Date endTime);

    List<Map<String,Object>> statisticalOldPassengerFlowByMonthER(Date startTime, Date endTime);

    List<Map<String,Object>> statisticalNewPassengerFlowByDay(Date startTime, Date endTime);

    List<Map<String,Object>> statisticalNewPassengerFlowByWeek(Date startTime, Date endTime);

    List<Map<String,Object>> statisticalNewPassengerFlowByMonth(Date startTime, Date endTime);

    List<Map<String,Object>> statisticalNewPassengerFlowByMonthER(Date startTime, Date endTime);

    Map<String,Object> statisticalPassengerFlowCount(Date date);

    List<Map<String,Object>> statisticalAgeCount(Date date);

    List<Map<String,Object>> statisticalGenderCount(Date date);
   
}
