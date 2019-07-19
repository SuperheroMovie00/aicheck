/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.visitorsRecord.controller;

import com.alibaba.fastjson.JSON;
import com.aicheck.face.modules.customer.vo.CustomerVO;
import com.aicheck.face.modules.device.constants.DeviceEnum;
import com.aicheck.face.modules.device.entity.Device;
import com.aicheck.face.modules.device.service.DeviceService;
import com.aicheck.face.modules.nettyPush.GlobalUser;
import com.aicheck.face.modules.nettyPush.Message;
import com.aicheck.face.modules.nettyPush.MessageTypeEnum;
import com.aicheck.face.modules.sync.entity.sync;
import com.aicheck.face.modules.sync.service.syncService;
import com.aicheck.face.modules.visitorsRecord.entity.VisitorsRecord;
import com.aicheck.face.modules.visitorsRecord.entity.VisitorsRecordForm;
import com.aicheck.face.modules.visitorsRecord.service.VisitorsRecordService;
import com.aicheck.face.vo.R;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 7:43 PM 2018/11/27
 */
@RestController
@RequestMapping("/v1/visitors-record")
@Slf4j
public class VisitorsRecordController {
	@Autowired
	private VisitorsRecordService visitorsRecordService;
	@Autowired
	private syncService syncservice;
	@Autowired
	private DeviceService deviceService;

	@GetMapping
	public R findAllList() {
		List<VisitorsRecord> visitorsRecords = visitorsRecordService.findAll();
		Map<String, Object> map = new HashMap<>();
		map.put("visitorsRecords", visitorsRecords);
		return R.ok(map);
	}

	@PostMapping("/{id}")
	public R findById(@PathVariable Integer id) {
		VisitorsRecord visitorsRecord = visitorsRecordService.findById(id);
		return R.ok(visitorsRecord);
	}

	
	//统计前的数据处理
	@GetMapping("/test")
	public R test() {
		visitorsRecordService.disposition();
		return R.ok();
	}
	
	@PostMapping
	public R save(@RequestBody VisitorsRecordForm visitorsRecordForm) {

		System.out.println("进入同步方法");

		log.info("新客识别 ->{}", JSON.toJSONString(visitorsRecordForm));

		VisitorsRecord visitorsRecord = new VisitorsRecord(); // 声明新的对象

		BeanUtils.copyProperties(visitorsRecordForm, visitorsRecord); // 将数据同步到visitorsRecord类的对象中

		visitorsRecord = visitorsRecordService.save(visitorsRecord);

		log.info("新客识别数据新增成功");

		// 推送至手机
		List<Device> deviceList = deviceService.findByPlatform(DeviceEnum.MOBILE.getValue()); // 根据平台查询设备

		List<String> ipList = deviceList.stream().map(Device::getIpAddress).collect(Collectors.toList());

		// 中间表中插入数据
		String ipString = "";
		String deviceString = "";
		sync sy = new sync();

		for (int m = 0; m < deviceList.size(); m++) {
			deviceString += deviceList.get(m) + ",";
		}
		String deviceStringnew = deviceString.substring(0, deviceString.length() - 1);

		sy.setFunc(deviceStringnew);
		sy.setCreateTime(new Date());

		for (int r = 0; r < ipList.size(); r++) {
			ipString += ipList.get(r) + ",";
		}
		String ipStringnew = ipString.substring(0, ipString.length() - 1);

		sy.setReceiver(ipStringnew);
		sy.setStatus(0);

		sync sync = syncservice.save(sy);
		if(sync!=null) {
			
		}
		// 存入sync 表之后此程序结束 。 调用线程开始同步机器
		
		  GlobalUser.channels.forEach(channel -> { if
		  (ipList.contains(channel.remoteAddress().toString())) { //判断ipList是否是
		 
		  Message message = new Message();
		  
		  message.setAction(MessageTypeEnum.NEW_CUSTOMER.getValue());
		  
		  message.setObject(new CustomerVO());
		  
		  channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
		  
		  }
		   });
		 

		return R.ok(visitorsRecord);
	}

}
