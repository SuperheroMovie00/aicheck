/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.customer.utils;

import com.alibaba.fastjson.JSON;
import com.aicheck.face.common.utils.SpringContextUtils;
import com.aicheck.face.modules.customer.vo.CustomerVO;
import com.aicheck.face.modules.device.entity.Device;
import com.aicheck.face.modules.device.entity.TodoPush;
import com.aicheck.face.modules.device.service.DeviceService;
import com.aicheck.face.modules.device.service.TodoPushService;
import com.aicheck.face.modules.nettyPush.GlobalUser;
import com.aicheck.face.modules.nettyPush.Message;
import com.aicheck.face.modules.nettyPush.MessageTypeEnum;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 11:15 AM 2019/1/24
 */
public class CustomerPushBoxUtils {

	public static void nettyPush(String type, Object object) {
		Message message = new Message();
		message.setAction(type); // 成功失败
		message.setObject(object); // 类型
		Integer[] array=null;

		String str = JSON.toJSONString(message);
		push(str, type,array);
	}

	public static void nettyPusher(String type, CustomerVO object) {
		Message message = new Message();
		message.setAction(type);
		message.setObject(object);
		message.setId(-1);
		String str = JSON.toJSONString(message);
		pusher(str, type,object.getId());
	}
	// 新增的发送单条数据的方法
	public static void nettyPushnew(String type, Object object, String ipaddress, int index) {
		Message message = new Message();
		message.setAction(type);
		message.setObject(object);
		message.setId(index); // 将传输的id推送过去
		String str = JSON.toJSONString(message);
		pushnew(str, type, ipaddress);
	}

	public static void nettyPush(String type, Integer[] array) {
		Message message = new Message();
		message.setAction(type);
		message.setId(-1);
		message.setArray(array);
		String str = JSON.toJSONString(message);
		push(str, type,array);
	}


	public static void nettyPush3(String type, Integer[] array,int id) {
		Message message = new Message();
		message.setAction(type);
		message.setId(id);
		message.setArray(array);

		String str = JSON.toJSONString(message);
		push(str, type,array);
	}

	// 新增的发送单条ip数据的发送方法
	private static void pushnew(String message, String type, String ipaddress) {

		// 相当于@Autowired
		TodoPushService todoPushService = (TodoPushService) SpringContextUtils.getBean("todoPushService");
		DeviceService deviceService = (DeviceService) SpringContextUtils.getBean("deviceService");

		//List<Device> deviceListc = deviceService.findAllByRegisterType(); // 获取注册方式为空的设备
		//List<String> ipListc = deviceListc.stream().map(Device::getIpAddress).collect(Collectors.toList());

		List<String> ipList = new ArrayList<>();
		List<Device> deviceList = new ArrayList<>();
		ipList.add(ipaddress);
		deviceList.add(deviceService.findAllByforipaddress(ipaddress));

		GlobalUser.channels.forEach(channel -> {
			if(channel.remoteAddress().equals(ipList.get(0))) {
				ipList.remove(channel.remoteAddress().toString());
				channel.writeAndFlush(new TextWebSocketFrame(message));
			}
		});
		

		if (!CollectionUtils.isEmpty(ipList)) {
			// 添加待发消息
			List<TodoPush> todoPushList = new ArrayList<>();
			List<String> macList = deviceList.stream().filter(device -> ipList.contains(device.getIpAddress()))
					.map(Device::getMacAddress).collect(Collectors.toList());
			macList.forEach(s -> {
				TodoPush todoPush = new TodoPush();
				todoPush.setDeviceId(s);
				todoPush.setMessage(message);
				todoPush.setType(MessageTypeEnum.getMessageTypeEnum(type).getCode());
				todoPushList.add(todoPush);
			});

			todoPushService.save(todoPushList);

		}

	}
	
	private static void pusher(String message, String type,Integer customerid) {	
		// 相当于@Autowired
		TodoPushService todoPushService = (TodoPushService) SpringContextUtils.getBean("todoPushService");
		DeviceService deviceService = (DeviceService) SpringContextUtils.getBean("deviceService");

		//2019/6/6		List<Device> deviceList = deviceService.findAllByRegisterType(); // 获取注册方式为空的设备   
		
		List<Device> deviceList = deviceService.findAllByforplatform("box"); // 获取注册方式为空的设备
		List<String> ipList = deviceList.stream().map(Device::getIpAddress).collect(Collectors.toList());
		
		if (!CollectionUtils.isEmpty(ipList)) {
			// 添加待发消息
			List<TodoPush> todoPushList = new ArrayList<>();
			List<String> macList = deviceList.stream().filter(device -> ipList.contains(device.getIpAddress()))
					.map(Device::getMacAddress).collect(Collectors.toList());

			System.out.println("记录集合" + todoPushList);
			System.out.println("MAC地址集合" + macList);
			macList.forEach(s -> {
				TodoPush todoPush = new TodoPush();
				todoPush.setDeviceId(s);
				todoPush.setMessage(message);
				todoPush.setType(MessageTypeEnum.getMessageTypeEnum(type).getCode());
				todoPush.setDataId(customerid);
				todoPushList.add(todoPush);
			});

			todoPushService.save(todoPushList);

		}
	}
	

	private static void push(String message, String type,Integer[] array) {
		// 相当于@Autowired
		TodoPushService todoPushService = (TodoPushService) SpringContextUtils.getBean("todoPushService");
		DeviceService deviceService = (DeviceService) SpringContextUtils.getBean("deviceService");

		//List<Device> deviceList = deviceService.findAllByRegisterType(); // 获取注册方式为空的设备
		List<Device> deviceList = deviceService.findAllByRegisterTypeonlybox(); // 获取注册方式为空的设备
		if(deviceList!=null){
			System.out.println("deviceList取出成功");
		}else{
			System.out.println("deviceList取失败");
		}
		List<String> ipList = deviceList.stream().map(Device::getIpAddress).collect(Collectors.toList());
		
		if(type.equals("delete")){
		}else{

		GlobalUser.channels.forEach(channel -> {

			ipList.remove(channel.remoteAddress().toString());

			channel.writeAndFlush(new TextWebSocketFrame(message));

		});
		}

		if (!CollectionUtils.isEmpty(ipList)) {
			// 添加待发消息
			List<TodoPush> todoPushList = new ArrayList<>();
			List<String> macList = deviceList.stream().filter(device -> ipList.contains(device.getIpAddress()))
					.map(Device::getMacAddress).collect(Collectors.toList());

			System.out.println("记录集合" + todoPushList);
			System.out.println("MAC地址集合" + macList);
			macList.forEach(s -> {
				TodoPush todoPush = new TodoPush();
				todoPush.setDeviceId(s);
				todoPush.setMessage(message);
				todoPush.setType(MessageTypeEnum.getMessageTypeEnum(type).getCode());
				todoPush.setDataId(array[0]);
				todoPushList.add(todoPush);
			});

					List<TodoPush> todoPush= todoPushService.save(todoPushList);
					if(todoPush!=null){
						System.out.println("todoPush新增成功");
					}else{
						System.out.println("todoPush新增失败ABCD");
					}
		}
	}

}
