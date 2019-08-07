/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.device.repository;

import com.aicheck.face.modules.device.entity.TodoPush;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:49 PM 2019/1/25
 */
public interface TodoPushRepository extends JpaRepository<TodoPush,Integer> {

    @Query(value = "select * from todo_push where device_id = ?1 and status is null",nativeQuery = true)
    List<TodoPush> findByDeviceIdAndStatusNot(String deviceId);
    
    @Query(value = "select * from todo_push where id=?1 and status=1  and  ?2=?3 ",nativeQuery = true)
    TodoPush findbyid(Integer id,Integer a,Integer b);
}
