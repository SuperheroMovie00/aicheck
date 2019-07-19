/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.device.service.impl;

import com.aicheck.face.modules.device.entity.TodoPush;
import com.aicheck.face.modules.device.repository.TodoPushRepository;
import com.aicheck.face.modules.device.service.TodoPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:51 PM 2019/1/25
 */
@Service("todoPushService")
public class TodoPushServiceImpl implements TodoPushService {
    @Autowired
    private TodoPushRepository todoPushRepository;
    @Override
    public TodoPush save(TodoPush todoPush) {
        return todoPushRepository.save(todoPush);
    }

    @Override
    public List<TodoPush> save(List<TodoPush> t) {
        return todoPushRepository.saveAll(t);
    }

    @Override
    public void delete(Integer integer) {
        todoPushRepository.deleteById(integer);
    }

    @Override
    public TodoPush update(TodoPush todoPush) {
        return todoPushRepository.save(todoPush);
    }

    @Override
    public List<TodoPush> find(TodoPush todoPush) {
        return null;
    }

    @Override
    public List<TodoPush> findAll() {
        return todoPushRepository.findAll();
    }

    @Override
    public TodoPush findById(Integer integer) {
        Optional<TodoPush> optional = todoPushRepository.findById(integer);

        return optional.isPresent()?optional.get():null;
    }

    @Override
    public List<TodoPush> findByDeviceId(String deviceId) {

        return todoPushRepository.findByDeviceIdAndStatusNot(deviceId);
    }

	@Override
	public TodoPush findbyid(Integer id,Integer a,Integer b) {
		// TODO Auto-generated method stub
		return todoPushRepository.findbyid(id,a,b);
	}
}
