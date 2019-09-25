/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.trafficStatistics.schedule;

import com.aicheck.face.modules.pathseting.entity.pathseting;
import com.aicheck.face.modules.pathseting.service.pathsetingService;
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
import com.aicheck.face.modules.system.entity.SystemParameter;
import com.aicheck.face.modules.system.service.SystemParameterService;
import com.aicheck.face.modules.trafficStatistics.entity.TrafficStatistics;
import com.aicheck.face.modules.trafficStatistics.service.TrafficStatisticsService;
import com.aicheck.face.modules.visitorsRecord.entity.Videostatistic;
import com.aicheck.face.modules.visitorsRecord.service.VideostatisticService;
import com.netsdk.demo.VideoStatistic;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aicheck.face.vo.file.deleteDir;


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
    private pathsetingService pathsetingService;
    @Autowired
    private syncService syncservice;
    @Autowired
    private CustomerService customerservice;
    @Autowired
    private VideostatisticService videostatisticService;
    @Autowired
    private SystemParameterService systemparameterservice;

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
     * 同步数据使用的任务调度器(注册识别)
     */

    @Scheduled(cron = "*/10 * * * * ? ")
    public void startsync() {

        log.info("调用！！！！！！！！！！！！！！" + new Date());
        if (GlobalUser.channels.size() != 0) {

            GlobalUser.channels.forEach(channel -> {
                //log.info("有设备进行同步操作");
                String ip;
                ip = channel.remoteAddress().toString();

                DeviceService deviceService = (DeviceService) SpringContextUtils.getBean("deviceService");
//			    Device device = deviceService.findAllByformacaddress(ip);
                Device device = deviceService.findAllByforipaddress(ip);
                if (device == null) {
                    return;
                }
                String s = device.getMacAddress();

                TodoPushService todoPushService = (TodoPushService) SpringContextUtils.getBean("todoPushService");
                //log.info("连接设备 -> {}", s, ip);

                List<TodoPush> todoPushList = todoPushService.findByDeviceId(s);
                if (!CollectionUtils.isEmpty(todoPushList)) {
                    //log.info("待发送消息设备 -> {},数量:{}", s, todoPushList.size());

                    todoPushList.forEach(todoPush -> {
                        Message message = new Message();

                        // 将user_model_value字符串类型的转成UserModel对象类型

                        if (todoPush.getType() == 4) {
                            message.setAction(MessageTypeEnum.DELETE.getValue());
                            String tudomessage = todoPush.getMessage();
                            tudomessage = tudomessage.replace(":-1", ":" + todoPush.getId());
                            //log.info("删除同步开始******");
                            channel.writeAndFlush(new TextWebSocketFrame(tudomessage));
                            //log.info("删除同步发送完成******");
                            todoPush.setMessage(tudomessage);
                            todoPush.setSend_time(new Date());             //更新todopush的发送时间
                            todoPushService.save(todoPush);
                        }

                        if (todoPush.getType() == 3) {
                            message.setAction(MessageTypeEnum.UPDATE.getValue());
                            String tudomessage = todoPush.getMessage();
                            tudomessage = tudomessage.replace(":-1", ":" + todoPush.getId());
                            //log.info("修改同步开始******");
                            channel.writeAndFlush(new TextWebSocketFrame(tudomessage));
                            //log.info("修改同步发送完成******");
                            todoPush.setMessage(tudomessage);
                            todoPush.setSend_time(new Date());             //更新todopush的发送时间
                            todoPushService.save(todoPush);
                        }

					/*if (todoPush.getType()==3 && todoPush.getDataId()!=null){  //类型为3 并且data_id不为空

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
						log.info("新增同步开始******");
						channel.writeAndFlush(new TextWebSocketFrame(str));
						log.info("新增同步结束******");
						todoPush.setSend_time(new Date());             //更新todopush的发送时间
						todoPushService.save(todoPush);
					}*/

                        if (todoPush.getType() == 2) {

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
                            //log.info("新增同步开始******");
                            channel.writeAndFlush(new TextWebSocketFrame(str));
                            //log.info("新增同步结束******");
                            todoPush.setSend_time(new Date());             //更新todopush的发送时间
                            todoPushService.save(todoPush);
                        }


                        //log.info("待发送消息 id :{} ,发送成功", todoPush.getId(), ip);
                    });
                }
            });
        } else {

            //log.info("没有链接设备");
        }
        // CustomerPushBoxUtils.nettyPushnew(MessageTypeEnum.SAVE.getValue(),
        // customerVO,ipaddress,syncid);

        /*
         * SystemParameter.out.println("调用注册会员同步*******************"); List<sync> synclist =
         * syncservice.querysynclist(0, "save");
         *
         * if (synclist != null) { for (int r = 0; r < synclist.size(); r++) { Customer
         * cus = customerservice.findById(synclist.get(r).getDataId()); String
         * ipaddress=synclist.get(r).getReceiver(); Integer
         * syncid=synclist.get(r).getId();
         *
         * SystemParameter.out.println(cus); CustomerVO customerVO = new CustomerVO();
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
     * 定时清理访客资源目录的资源
     */
    //@Scheduled(cron = "* * * * */6 ? ")
    public void deleteadvertisingimages() {
    	 JSONObject json = null;
        pathseting pathempty = pathsetingService.findpathfortype("visitors");

        if (pathempty == null) {
            return;
        }
        /**
         * 清理文件夹中的文件
         */
        String path = pathempty.getPath();
        deleteDir(path);
        log.info("清理访客文件夹");
    }

    /**
     * 定时统计流量(每小时)
     * @throws ParseException 
     */
    //@Scheduled(cron = "0 0/6 * * * ?")
    //@Scheduled(cron = "*/10 * * * * ? ")
    public void flowstatisticshour() throws ParseException{
    	System.out.println("调用统计");
    	VideoStatistic demo = new VideoStatistic();
    	demo.InitTest();
    	JSONObject json = null;
    	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat simpleDateFormatdd=new SimpleDateFormat("dd");
        SystemParameter systemParameter=systemparameterservice.querysystem("day");
        String dayendtime=systemParameter.getSystemValue();
        //将最后的统计时间与 当前时间进行比较
        Date dayendtimesf=simpleDateFormat.parse(dayendtime);
        if(!simpleDateFormatdd.format(new Date()).equals(simpleDateFormatdd.format(dayendtimesf))) {
        	for(int r=Integer.parseInt(simpleDateFormatdd.format(dayendtimesf));r<Integer.parseInt(simpleDateFormatdd.format(new Date()));r++) {
        		//先将日期为这个时间的数据都删掉，然后再将时间调回到这个时间进行24时的统计。
        		SimpleDateFormat simpleDateFormatc=new SimpleDateFormat("yyyy-MM-dd");
        		String datestr=simpleDateFormatc.format(dayendtimesf);
        		videostatisticService.deletevideostatistic(datestr, "day");
        		
        		for(int l=1;l<24;l++) {
        		Date cccdata=dayendtimesf;
        		Calendar calendar = Calendar.getInstance();
                calendar.setTime(cccdata);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(cccdata);
                calendar2.set(Calendar.HOUR_OF_DAY, 0);
                
                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + l);
                calendar2.set(Calendar.HOUR_OF_DAY, calendar2.get(Calendar.HOUR_OF_DAY) + l + 1);
                
                Map<String, Integer> cMap = VideoStatistic.startFindNumberStatrewrite(calendar.getTime(), calendar2.getTime(), 1);
        		
                Integer innum = cMap.get("IN");
                Integer outnum = cMap.get("OUT");
                json = JSONObject.fromObject(cMap);
                Videostatistic addvideostatistic = new Videostatistic();
                addvideostatistic.setDateType("day");
                addvideostatistic.setDateInformation(json.toString());
                addvideostatistic.setCreateTime(calendar.getTime());
                videostatisticService.save(addvideostatistic);
        		
        		}
        		SimpleDateFormat simpleDateFormatHH=new SimpleDateFormat("HH");
        		Date crea=null;
        		for(int c=1;c<Integer.parseInt(simpleDateFormatHH.format(new Date()));c++) {
        			Date cccdata=new Date();
        			
        			Calendar calendar = Calendar.getInstance();
                    calendar.setTime(cccdata);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(cccdata);
                    calendar2.set(Calendar.HOUR_OF_DAY, 0);
                    
                    calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + c);
                    calendar2.set(Calendar.HOUR_OF_DAY, calendar2.get(Calendar.HOUR_OF_DAY) + c + 1);
                    
                    Map<String, Integer> cMap = VideoStatistic.startFindNumberStatrewrite(calendar.getTime(), calendar2.getTime(), 1);
            		
                    Integer innum = cMap.get("IN");
                    Integer outnum = cMap.get("OUT");
                    json = JSONObject.fromObject(cMap);
                    Videostatistic addvideostatistic = new Videostatistic();
                    addvideostatistic.setDateType("day");
                    addvideostatistic.setDateInformation(json.toString());
                    addvideostatistic.setCreateTime(calendar.getTime());
                    videostatisticService.save(addvideostatistic);
                    crea=calendar2.getTime();
        		}
        		
        		SystemParameter system=systemparameterservice.querysystem("day");
                system.setSystemValue(simpleDateFormat.format(crea));
                systemparameterservice.save(system);
        		
        	}
        }else {
        	SimpleDateFormat simpleDateFormatHH=new SimpleDateFormat("HH");
        	
        	if(Integer.parseInt(simpleDateFormatHH.format(new Date()))-Integer.parseInt(simpleDateFormatHH.format(dayendtimesf))!=1) {
        		SimpleDateFormat simpleDateFormatc=new SimpleDateFormat("yyyy-MM-dd");
        		String datestr=simpleDateFormatc.format(dayendtimesf);
        		videostatisticService.deletevideostatistic(datestr, "day");
        		Date crea=null;
        		for(int c=1;c<Integer.parseInt(simpleDateFormatHH.format(dayendtimesf));c++) {
        			Date cccdata=dayendtimesf;
        			
        			Calendar calendar = Calendar.getInstance();
                    calendar.setTime(cccdata);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(cccdata);
                    calendar2.set(Calendar.HOUR_OF_DAY, 0);
                    
                    calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + c);
                    calendar2.set(Calendar.HOUR_OF_DAY, calendar2.get(Calendar.HOUR_OF_DAY) + c + 1);
                    
                    Map<String, Integer> cMap = VideoStatistic.startFindNumberStatrewrite(calendar.getTime(), calendar2.getTime(), 1);
            		
                    Integer innum = cMap.get("IN");
                    Integer outnum = cMap.get("OUT");
                    json = JSONObject.fromObject(cMap);
                    Videostatistic addvideostatistic = new Videostatistic();
                    addvideostatistic.setDateType("day");
                    addvideostatistic.setDateInformation(json.toString());
                    addvideostatistic.setCreateTime(calendar.getTime());
                    videostatisticService.save(addvideostatistic);
                    crea=calendar2.getTime();
        		}
        		
        		SystemParameter system=systemparameterservice.querysystem("day");
                system.setSystemValue(simpleDateFormat.format(crea));
                systemparameterservice.save(system);
        			
        	}else {
        		
            	Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date); 

                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(date);
                   
                calendar2.set(Calendar.HOUR_OF_DAY, calendar2.get(Calendar.HOUR_OF_DAY) - 1);

                Map<String, Integer> map=   VideoStatistic.startFindNumberStatrewrite(calendar2.getTime(),calendar.getTime(),1);
                json = JSONObject.fromObject(map);
                Videostatistic addvideostatistic = new Videostatistic();
                addvideostatistic.setDateType("day");
                addvideostatistic.setDateInformation(json.toString());
                addvideostatistic.setCreateTime(calendar2.getTime());
                videostatisticService.save(addvideostatistic);	
                SystemParameter system=systemparameterservice.querysystem("day");
                system.setSystemValue(simpleDateFormat.format(calendar2.getTime()));
                systemparameterservice.save(system);
        	}
	
        }	
       
    
    }

    /**
     * 每天
     * @throws ParseException 
     */
	//@Scheduled(cron = "0 59 23 * * ?")
//	@Scheduled(cron = "*/10 * * * * ? ")
	public void flowstatisticsday() throws ParseException {
		VideoStatistic demo = new VideoStatistic();
    	demo.InitTest();
		JSONObject json = null;
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat simpleDateFormatdd=new SimpleDateFormat("dd");
		SystemParameter systemParameter=systemparameterservice.querysystem("week");
		
		String dayendtime=systemParameter.getSystemValue();
        //将最后的统计时间与 当前时间进行比较
        Date dayendtimesf=simpleDateFormat.parse(dayendtime);
       int c=0;
       Date crea=null;
        if(Integer.parseInt(simpleDateFormatdd.format(new Date()))-Integer.parseInt(simpleDateFormatdd.format(dayendtimesf))>1) { //如果两者不相等
        	for(int r=Integer.parseInt(simpleDateFormatdd.format(dayendtimesf));r<Integer.parseInt(simpleDateFormatdd.format(new Date()));r++) {
        		Date cccdate=dayendtimesf;
        		Calendar calendar = Calendar.getInstance();
                calendar.setTime(cccdate);        		
                calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + c);
                Map<String, Integer> map=   VideoStatistic.startFindNumberStatrewrite(calendar.getTime(),calendar.getTime(),2);
               
                json = JSONObject.fromObject(map);
                Videostatistic addvideostatistic = new Videostatistic();
                addvideostatistic.setDateType("week");
                addvideostatistic.setDateInformation(json.toString());
                addvideostatistic.setCreateTime(calendar.getTime());
                videostatisticService.save(addvideostatistic);
                crea=calendar.getTime();
                c++;
        	}
        	SystemParameter system=systemparameterservice.querysystem("week");
            system.setSystemValue(simpleDateFormat.format(crea));
            systemparameterservice.save(system);
      
        }else if(Integer.parseInt(simpleDateFormatdd.format(new Date()))-Integer.parseInt(simpleDateFormatdd.format(dayendtimesf))==1) {
        	Date date=new Date();
    		Map<String, Integer> map= VideoStatistic.startFindNumberStatrewrite(date, date, 2);
    		 json = JSONObject.fromObject(map);
    		Videostatistic addvideostatistic = new Videostatistic();
            addvideostatistic.setDateType("week");
            addvideostatistic.setDateInformation(json.toString());
            addvideostatistic.setCreateTime(date);
            videostatisticService.save(addvideostatistic);
            SystemParameter system=systemparameterservice.querysystem("week");
	        system.setSystemValue(simpleDateFormat.format(date));
	        systemparameterservice.save(system);
            
        }	
		
	}

	
	
	//@Scheduled(cron = "0 0 0 1 * ?")    //每月一号零点触发
//	@Scheduled(cron = "*/10 * * * * ? ")
	public void ccccc() throws ParseException {
		VideoStatistic demo = new VideoStatistic();
    	demo.InitTest();
		JSONObject json = null;
		
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat simpleDateFormatMM=new SimpleDateFormat("MM");
    	SystemParameter systemParameter=systemparameterservice.querysystem("year");
		
		String dayendtime=systemParameter.getSystemValue();
        //将最后的统计时间与 当前时间进行比较
        Date dayendtimesf=simpleDateFormat.parse(dayendtime);
		if(Integer.parseInt(simpleDateFormatMM.format(new Date()))-Integer.parseInt(simpleDateFormatMM.format(dayendtimesf))>1) {
		
		int c=0;
		Date crea=null;
		for (int i = Integer.parseInt(simpleDateFormatMM.format(dayendtimesf)); i < Integer.parseInt(simpleDateFormatMM.format(new Date())); i++) {
			Date cccdate=dayendtimesf;
    		Calendar calendar = Calendar.getInstance();
            calendar.setTime(cccdate);        		
            
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + c);
            Map<String, Integer> map=   VideoStatistic.startFindNumberStatrewrite(calendar.getTime(),calendar.getTime(),4);
            json = JSONObject.fromObject(map);
            Videostatistic addvideostatistic = new Videostatistic();
            addvideostatistic.setDateType("year");
            addvideostatistic.setDateInformation(json.toString());
            addvideostatistic.setCreateTime(calendar.getTime());
            videostatisticService.save(addvideostatistic);	
            crea=calendar.getTime();
			c++;
		}
		SystemParameter system=systemparameterservice.querysystem("year");
        system.setSystemValue(simpleDateFormat.format(crea));
        systemparameterservice.save(system);
		
		}else if(Integer.parseInt(simpleDateFormatMM.format(new Date()))-Integer.parseInt(simpleDateFormatMM.format(dayendtimesf))==1) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1); //得到前一天
			Date date = calendar.getTime();
			
			Map<String, Integer> map= VideoStatistic.startFindNumberStatrewrite(date, date, 4);
			
			json = JSONObject.fromObject(map);
			Videostatistic addvideostatistic = new Videostatistic();
	        addvideostatistic.setDateType("year");
	        addvideostatistic.setDateInformation(json.toString());
	        addvideostatistic.setCreateTime(date);
	        videostatisticService.save(addvideostatistic);
	        
	        SystemParameter system=systemparameterservice.querysystem("year");
	        system.setSystemValue(simpleDateFormat.format(date));
	        systemparameterservice.save(system);	        
		}
		
	}

	
	@Scheduled(cron = "0 20 18 ? * *")
	public void ccc() {	
		System.out.println("ddddddddddddd突然出现");
	}
	
	
	/**
	 * 每天晚上23点59分的时候,统计这一天的客流量
	 */
	@Scheduled(cron = "0 59 23 ? * *")
	//@Scheduled(cron = "*/10 * * * * ? ")
	public void videodaystatistics() {
		Videostatistic videostatistic= videostatisticService.findvideostatisicfortype("day");
		
		VideoStatistic demo = new VideoStatistic();
        demo.InitTest();
    	Map<String, Object> requestmap = new HashMap<String, Object>();
        JSONObject json = null;
		List<String> day = new ArrayList<String>();
        List<String> incount = new ArrayList<String>();
        List<String> outcount = new ArrayList<String>();
        Date date1 = new Date();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        int Number = calendar1.get(Calendar.HOUR_OF_DAY);

        for (int r = 0; r < 24; r++) {
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date);
            calendar2.set(Calendar.HOUR_OF_DAY, 0);
            
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + r);
            calendar2.set(Calendar.HOUR_OF_DAY, calendar2.get(Calendar.HOUR_OF_DAY) + r + 1);
            Map<String, Integer> cMap = VideoStatistic.startFindNumberStatrewrite(calendar.getTime(), calendar2.getTime(), 1);
            
            Integer innum = cMap.get("IN");
            Integer outnum = cMap.get("OUT");
            json = JSONObject.fromObject(cMap);
            
            incount.add(String.valueOf(innum));
            outcount.add(String.valueOf(outnum));
            day.add(String.valueOf(r)+"时");
        }
		
		if(videostatistic!=null) {
			requestmap.put("day", day);
	        requestmap.put("incount", incount);
	        requestmap.put("outcount", outcount);
	        json = JSONObject.fromObject(requestmap);
	        videostatistic.setDateType("day");
	        videostatistic.setDateInformation(json.toString());
	        videostatistic.setModificationTime(new Date());
	        videostatisticService.save(videostatistic);
		}else {
			Videostatistic videostatisticday=new Videostatistic();
			requestmap.put("day", day);
	        requestmap.put("incount", incount);
	        requestmap.put("outcount", outcount);
	        json = JSONObject.fromObject(requestmap);
	        videostatisticday.setDateType("day");
	        videostatisticday.setDateInformation(json.toString());
	        videostatisticday.setCreateTime(new Date());
	        videostatisticService.save(videostatisticday);
		}
		demo.EndTest();				//解决连接数到限的问题
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
                        } else {
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
