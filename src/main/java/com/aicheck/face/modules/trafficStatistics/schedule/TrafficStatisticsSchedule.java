/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.trafficStatistics.schedule;

import com.alibaba.fastjson.JSON;

import com.aicheck.face.common.utils.SpringContextUtils;
import com.aicheck.face.modules.customer.entity.Customer;
import com.aicheck.face.modules.customer.entity.UserModel;
import com.aicheck.face.modules.customer.service.CustomerService;

import com.aicheck.face.modules.customer.vo.CustomerVO;
import com.aicheck.face.modules.device.entity.Device;
import com.aicheck.face.modules.device.entity.TodoPush;
import com.aicheck.face.modules.device.service.DeviceService;
import com.aicheck.face.modules.device.service.TodoPushService;
import com.aicheck.face.modules.nettyPush.GlobalUser;
import com.aicheck.face.modules.nettyPush.Message;
import com.aicheck.face.modules.nettyPush.MessageTypeEnum;
import com.aicheck.face.modules.socket.WebSocketEndPoint;
import com.aicheck.face.modules.sync.entity.sync;
import com.aicheck.face.modules.sync.service.syncService;
import com.aicheck.face.modules.trafficStatistics.entity.TrafficStatistics;
import com.aicheck.face.modules.trafficStatistics.service.TrafficStatisticsService;


import io.netty.channel.ChannelFuture;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;


import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import java.io.IOException;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 7:37 PM 2018/12/26
 */
@Configuration
@Component
@Slf4j
public class TrafficStatisticsSchedule {
	static int ccc = 0;
	@Autowired
	private TrafficStatisticsService trafficStatisticsService;

	@Autowired
	private syncService syncservice;
	@Autowired
	private CustomerService customerservice;

	//date :jar 包运行报错  @Scheduled(cron = "0 59 * * * ?")
	public void saveTrafficStatistics() {
		// 每小时59分钟 获取 计数数据

		int[] numbers = trafficStatisticsService.getTrafficStatistics();

		TrafficStatistics trafficStatistics = new TrafficStatistics();
		trafficStatistics.setFlow(numbers[0]);
		trafficStatistics.setTotal(numbers[1]);

		trafficStatisticsService.save(trafficStatistics);

	}

	/**
	 *同步数据使用的任务调度器(注册识别)
 	 */

	@Scheduled(cron = "*/10 * * * * ? ")
	public void startsync() {

		System.out.println("调用！！！！！！！！！！！！！！"+new Date());
		if(GlobalUser.channels.size()!=0){

			GlobalUser.channels.forEach(channel -> {
			System.out.println("有设备进行同步操作");
			String ip;
			ip = channel.remoteAddress().toString();
			DeviceService deviceService = (DeviceService) SpringContextUtils.getBean("deviceService");
			Device device = deviceService.findAllByforipaddress(ip);
			/*if (device == null) {
				return;
			}*/
			String s = device.getMacAddress();

			TodoPushService todoPushService = (TodoPushService) SpringContextUtils.getBean("todoPushService");
			log.info("连接设备 -> {}", s, ip);

			List<TodoPush> todoPushList = todoPushService.findByDeviceId(s);
			if (!CollectionUtils.isEmpty(todoPushList)) {
				log.info("待发送消息设备 -> {},数量:{}", s, todoPushList.size());

				todoPushList.forEach(todoPush -> {
					Message message = new Message();

					// 将user_model_value字符串类型的转成UserModel对象类型


					if(todoPush.getType()==4){
						message.setAction(MessageTypeEnum.DELETE.getValue());
						String tudomessage=todoPush.getMessage();
						tudomessage=tudomessage.replace(":-1",":"+todoPush.getId());
						System.out.println("删除同步开始******");
						channel.writeAndFlush(new TextWebSocketFrame(tudomessage));
						System.out.println("删除同步发送完成******");
						todoPush.setMessage(tudomessage);
						todoPush.setSend_time(new Date());             //更新todopush的发送时间
						todoPushService.save(todoPush);
					}
					if (todoPush.getType()==3){

						Customer cus = customerservice.findById(todoPush.getDataId());
						CustomerVO customerVO = new CustomerVO();
						BeanUtils.copyProperties(cus, customerVO);
						JSONObject jsonObject = JSONObject.fromObject(cus.getUserModelValue());
						UserModel stu = (UserModel) JSONObject.toBean(jsonObject, UserModel.class);
						customerVO.setUserModel(stu);

						message.setAction(MessageTypeEnum.SAVE.getValue());
						message.setObject(customerVO);
						message.setId(todoPush.getId()); // 将传输的id推送过去
						String str = JSON.toJSONString(message);
						System.out.println("新增同步开始******");
						channel.writeAndFlush(new TextWebSocketFrame(str));
						System.out.println("新增同步结束******");
						todoPush.setSend_time(new Date());             //更新todopush的发送时间
						todoPushService.save(todoPush);
					}

					if (todoPush.getType()==2){

						Customer cus = customerservice.findById(todoPush.getDataId());
						CustomerVO customerVO = new CustomerVO();
						BeanUtils.copyProperties(cus, customerVO);
						JSONObject jsonObject = JSONObject.fromObject(cus.getUserModelValue());
						UserModel stu = (UserModel) JSONObject.toBean(jsonObject, UserModel.class);
						customerVO.setUserModel(stu);

						message.setAction(MessageTypeEnum.SAVE.getValue());
						message.setObject(customerVO);
						message.setId(todoPush.getId()); // 将传输的id推送过去
						String str = JSON.toJSONString(message);
						System.out.println("新增同步开始******");
						channel.writeAndFlush(new TextWebSocketFrame(str));
						System.out.println("新增同步结束******");
						todoPush.setSend_time(new Date());             //更新todopush的发送时间
						todoPushService.save(todoPush);
					}




					log.info("待发送消息 id :{} ,发送成功", todoPush.getId(), ip);
				});
			}
		});
		}else{
			System.out.println("没有链接设备");
			log.info("没有链接设备");
		}
		// CustomerPushBoxUtils.nettyPushnew(MessageTypeEnum.SAVE.getValue(),
		// customerVO,ipaddress,syncid);

		/*
		 * System.out.println("调用注册会员同步*******************"); List<sync> synclist =
		 * syncservice.querysynclist(0, "save");
		 * 
		 * if (synclist != null) { for (int r = 0; r < synclist.size(); r++) { Customer
		 * cus = customerservice.findById(synclist.get(r).getDataId()); String
		 * ipaddress=synclist.get(r).getReceiver(); Integer
		 * syncid=synclist.get(r).getId();
		 * 
		 * System.out.println(cus); CustomerVO customerVO = new CustomerVO();
		 * BeanUtils.copyProperties(cus, customerVO);
		 * 
		 * // 将user_model_value字符串类型的转成UserModel对象类型
		 * 
		 * JSONObject jsonObject = JSONObject.fromObject(cus.getUserModelValue());
		 * UserModel stu = (UserModel) JSONObject.toBean(jsonObject, UserModel.class);
		 * customerVO.setUserModel(stu);
		 * 
		 * 
		 * try { CustomerPushBoxUtils.nettyPushnew(MessageTypeEnum.SAVE.getValue(),
		 * customerVO,ipaddress,syncid);
		 * 
		 * synclist.get(r).setSucessTime(new Date()); synclist.get(r).setStatus(0);
		 * 
		 * } catch (Exception e) { // TODO: handle exception
		 * synclist.get(r).setFailedTime(new Date());
		 * synclist.get(r).setFailedCount(synclist.get(r).getFailedCount() + 1); //
		 * 失败的话失败次数加一 } syncservice.save(synclist.get(r)); } }
		 */
	}

	/**
	 * 同步数据使用的定时器任务(游客识别)
 	 */
	
	//@Scheduled(cron = "*/10 * * * * ? ")
	public void visitorsync() {

		List<String> ipList = new ArrayList<>();
		// 根据状态查询
		List<sync> synclist = syncservice.querysynclist(0, "visitors");
		if (synclist != null) {

			for (int r = 0; r < synclist.size(); r++) {

				String[] ipListarr = synclist.get(r).getReceiver().split(",");
				for (int c = 0; c < ipListarr.length; c++) {
					ipList.add(ipListarr[c]);
				}

				GlobalUser.channels.forEach(channel -> {
					if (ipList.contains(channel.remoteAddress().toString())) {
						Message message = new Message();

						message.setAction(MessageTypeEnum.NEW_CUSTOMER.getValue());

						message.setObject(new CustomerVO());

						ChannelFuture channe = channel
								.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
						if (channe != null) {
							ccc = 1;
						}
						else {
							ccc = 0;
						}
						}
				});
				if (ccc == 1) {
					synclist.get(r).setSucessTime(new Date());
				} else {
					synclist.get(r).setFailedTime(new Date());
				}
				syncservice.save(synclist.get(r));
			}
		}
	}

	//date :jar 包运行报错     @Scheduled(cron = "0/3 * * * * ? ")
	public void pushCount() {

		int[] numbers = new int[2];
		try {
			numbers = trafficStatisticsService.getTrafficStatistics();
		} catch (Exception e) {
			 log.error("获取人数统计失败:{}", e.getMessage());
		}

		try {
			WebSocketEndPoint.sendMessageAll(String.valueOf(numbers[0]));
		} catch (IOException e) {
			e.printStackTrace();
			log.info("流量人数推送失败：{}", e.getMessage());
		}

	}

	@Bean
	public ThreadPoolTaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		return taskScheduler;
	}

}
