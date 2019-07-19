/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.device.service;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.device.entity.TodoPush;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:49 PM 2019/1/25
 */
public interface TodoPushService extends BaseService<TodoPush,Integer> {

    List<TodoPush> findByDeviceId(String deviceId);
    
    TodoPush findbyid(Integer id,Integer a,Integer b);
}
