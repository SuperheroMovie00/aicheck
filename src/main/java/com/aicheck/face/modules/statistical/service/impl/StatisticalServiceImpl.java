/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.statistical.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.aicheck.face.modules.customer.repository.CustomerRepository;
import com.aicheck.face.modules.statistical.service.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 11:04 AM 2018/11/26
 */
@Service("statisticalService")
public class StatisticalServiceImpl implements StatisticalService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Map<String, Object> statisticalInit(Date date) {
        Object[] objects = customerRepository.statisticalInit(date);
        Object object = objects[0];
        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(object));
        Map<String,Object> map = new HashMap<>();
        map.put("total",jsonArray.get(0));
        map.put("newNumber",jsonArray.get(1));
        map.put("sales",jsonArray.getDouble(2));
        return map;
    }

    @Override
    public Map<String, Object> statisticalGender() {
    	//List<Map> cMap=customerRepository.ccccc();
        return customerRepository.statisticalGender();
    }

    @Override
    public Object[] statisticalAge() {
        return customerRepository.statisticalAge();
    }

    @Override
    public List<Map<String, Object>> statisticalTeam(Date date,Integer tagId) {
        return customerRepository.statisticalTeam(date,tagId);
    }


    @Override
    public List<Map<String,Object>> statisticalOldPassengerFlowByDay(Date startTime, Date endTime) {
        return customerRepository.statisticalOldPassengerFlowByDay(startTime,endTime);
    }

    @Override
    public List<Map<String,Object>> statisticalOldPassengerFlowByWeek(Date startTime, Date endTime) {
        return customerRepository.statisticalOldPassengerFlowByWeek(startTime,endTime);
    }

    @Override
    public List<Map<String,Object>> statisticalOldPassengerFlowByMonth(Date startTime, Date endTime) {
        return customerRepository.statisticalOldPassengerFlowByMonth(startTime,endTime);
    }

    @Override
    public List<Map<String, Object>> statisticalOldPassengerFlowByMonthCustomer(Date startTime, Date endTime) {
        return customerRepository.statisticalOldPassengerFlowByMonthCustomer(startTime,endTime);
    }


    @Override
    public List<Map<String,Object>> statisticalOldPassengerFlowByMonthER(Date startTime, Date endTime) {
        return customerRepository.statisticalOldPassengerFlowByMonthER(startTime,endTime);
    }


    @Override
    public List<Map<String, Object>> statisticalNewPassengerFlowByDay(Date startTime, Date endTime) {
        return customerRepository.statisticalNewPassengerFlowByDay(startTime,endTime);
    }

    @Override
    public List<Map<String, Object>> statisticalNewPassengerFlowByWeek(Date startTime, Date endTime) {
        return customerRepository.statisticalNewPassengerFlowByWeek(startTime,endTime);
    }

    @Override
    public List<Map<String, Object>> statisticalNewPassengerFlowByMonth(Date startTime, Date endTime) {
        return customerRepository.statisticalNewPassengerFlowByMonth(startTime,endTime);
    }

    @Override
    public List<Map<String, Object>> statisticalNewPassengerFlowByMonthER(Date startTime, Date endTime) {
        return customerRepository.statisticalNewPassengerFlowByMonthER(startTime,endTime);
    }

    @Override
    public Map<String, Object> statisticalPassengerFlowCount(Date date) {
        return customerRepository.statisticalPassengerFlowCount(date);
    }

    @Override
    public List<Map<String, Object>> statisticalAgeCount(Date date) {
        return customerRepository.statisticalAgeCount(date);
    }

    @Override
    public List<Map<String, Object>> statisticalGenderCount(Date date) {
    	System.out.println("刚刚跟"+customerRepository.statisticalGenderCount(date).toString());
        return customerRepository.statisticalGenderCount(date);
    }
  
 
}
