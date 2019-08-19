/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.statistical.controller;

import com.aicheck.face.common.utils.BeanUtils;
import com.aicheck.face.modules.statistical.service.StatisticalService;
import com.aicheck.face.modules.visitorsRecord.entity.Videostatistic;
import com.aicheck.face.modules.visitorsRecord.service.VideostatisticService;
import com.aicheck.face.vo.R;
import com.alibaba.fastjson.JSON;
import com.netsdk.demo.VideoStatistic;


import antlr.collections.impl.IntRange;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.xml.stream.events.EndDocument;


/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 10:30 AM 2018/11/26
 */
@RestController
@RequestMapping("/v1/statistical")
public class StatisticalController {
    @Autowired
    private StatisticalService statisticalService;
    @Autowired
    private VideostatisticService videostatisticService;


    @GetMapping("/videoStatisticccc")
    public R videoStatisticccc() {
        VideoStatistic demo = new VideoStatistic();
        demo.InitTest();
        return R.ok();
    }


    @PostMapping("/videoStatistic")
    public R videoStatistic(Integer type,String datestr,Integer weektype) throws ParseException {
        Map<String, Object> requestmap = new HashMap<String, Object>();
        JSONObject json = null;
        String Information = null;
        /**
         * 按照天来统计
         */
        
        if (type == 4) {
        	
        	List<Videostatistic> Videostatisticlist=videostatisticService.findvideostatisicfordaylist(datestr);
        	System.out.println(Videostatisticlist);
        	if(!Videostatisticlist.isEmpty()) {
        	
        	List<String> day = new ArrayList<String>();
            List<String> incount = new ArrayList<String>();
            List<String> outcount = new ArrayList<String>();
        		
            int date=0;
        	for(Videostatistic vid:Videostatisticlist) {
        		SimpleDateFormat format=new SimpleDateFormat("HH");
        		Date createtime=vid.getCreateTime();
        		String createtimehour=format.format(createtime);
        		day.add(createtimehour+"时");
        		Map<String,Integer> maps = (Map<String,Integer>)JSON.parse(vid.getDateInformation());
        		Integer innum=maps.get("IN");
        		Integer outnum=maps.get("OUT");
        		incount.add(String.valueOf(innum));
        		outcount.add(String.valueOf(outnum));
        		date++;
        	}
        	
        	requestmap.put("day", day);
            requestmap.put("incount", incount);
            requestmap.put("outcount", outcount);
            json = JSONObject.fromObject(requestmap);
        	}else {	
        		List<String> day = new ArrayList<String>();
                List<String> incount = new ArrayList<String>();
                List<String> outcount = new ArrayList<String>();
                for(int r=0;r<24;r++) {
                	day.add(String.valueOf(r)+"时");
                	incount.add(String.valueOf(0));
                	outcount.add(String.valueOf(0));
                }
                requestmap.put("day", day);
                requestmap.put("incount", incount);
                requestmap.put("outcount", outcount);
                json = JSONObject.fromObject(requestmap);
        	}
        }
        
        if (type == 5) {
        	
        	SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");        
            String date4=null;
        	Date date4time=null;
        	if(weektype==1) {
        		Calendar cal = Calendar.getInstance();
        		  cal.set(cal.DAY_OF_WEEK, cal.MONDAY);
                   date4=formatter2.format(cal.getTime());
                   date4time=cal.getTime();
        		}else {
        			Calendar calendar = Calendar.getInstance();
        	        calendar.setTime(new Date());
        	        calendar.set(Calendar.SECOND, 0);
        	        calendar.set(Calendar.MINUTE, 0);
        	        calendar.set(Calendar.HOUR_OF_DAY, 0);
        	        calendar.add(Calendar.DATE, -8);
        	        calendar.set(calendar.DAY_OF_WEEK, calendar.MONDAY);
        	        date4=formatter2.format(calendar.getTime());
        	        date4time= calendar.getTime();
        	}
        	
        	
        	Videostatistic  Videostatisticlistyan=videostatisticService.findvideostatisicforweekyanlist(date4);
        	if(Videostatisticlistyan!=null) {
        		
        	
        	List<Videostatistic> Videostatisticlist=videostatisticService.findvideostatisicforweeklist(date4);
        	if (!Videostatisticlist.isEmpty()) {
				
			
        	List<String> day = new ArrayList<String>();
            List<String> incount = new ArrayList<String>();
            List<String> outcount = new ArrayList<String>();
        		
            SimpleDateFormat format=new SimpleDateFormat("dd");
            String dString=format.format(date4time);
            int date=Integer.parseInt(dString);
        	for(Videostatistic vid:Videostatisticlist) {
        		
        		day.add(String.valueOf(date)+"日");
        		Map<String,Integer> maps = (Map<String,Integer>)JSON.parse(vid.getDateInformation());
        		Integer innum=maps.get("IN");
        		Integer outnum=maps.get("OUT");
        		incount.add(String.valueOf(innum));
        		outcount.add(String.valueOf(outnum));
        		date++;
        	}
        	
        	requestmap.put("day", day);
            requestmap.put("incount", incount);
            requestmap.put("outcount", outcount);
            json = JSONObject.fromObject(requestmap);
        	
        	}
        	}else {
        		List<String> day = new ArrayList<String>();
                List<String> incount = new ArrayList<String>();
                List<String> outcount = new ArrayList<String>();
                
                SimpleDateFormat format=new SimpleDateFormat("dd");
                String dString=format.format(date4time);
                int date=Integer.parseInt(dString);
                
                for(int r=0;r<7;r++) {
                	day.add(String.valueOf(date)+"日");
                	incount.add(String.valueOf(0));
                	outcount.add(String.valueOf(0));
                	date++;
                }
                requestmap.put("day", day);
                requestmap.put("incount", incount);
                requestmap.put("outcount", outcount);
                json = JSONObject.fromObject(requestmap);
        	}
        }
        
        if (type == 6) {
        	Date date1=new Date();
        	SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy");
        	String year = formatter2.format(date1);
        	
        	List<Videostatistic> Videostatisticlist=videostatisticService.findvideostatisicforyearlist(year+"-");
        	if(!Videostatisticlist.isEmpty()) {
        		
        	
        	List<String> day = new ArrayList<String>();
            List<String> incount = new ArrayList<String>();
            List<String> outcount = new ArrayList<String>();
        		
            int date=1;
        	for(Videostatistic vid:Videostatisticlist) {
        		day.add(String.valueOf(date)+"月");
        		Map<String,Integer> maps = (Map<String,Integer>)JSON.parse(vid.getDateInformation());
        		Integer innum=maps.get("IN");
        		Integer outnum=maps.get("OUT");
        		incount.add(String.valueOf(innum));
        		outcount.add(String.valueOf(outnum));
        		date++;
        	}
        	
        	requestmap.put("day", day);
            requestmap.put("incount", incount);
            requestmap.put("outcount", outcount);
            json = JSONObject.fromObject(requestmap);
        	}	else {
        		List<String> day = new ArrayList<String>();
                List<String> incount = new ArrayList<String>();
                List<String> outcount = new ArrayList<String>();
                
                
                for(int r=1;r<=12;r++) {
                	day.add(String.valueOf(r)+"月");
                	incount.add(String.valueOf(0));
                	outcount.add(String.valueOf(0));
                
                }
                requestmap.put("day", day);
                requestmap.put("incount", incount);
                requestmap.put("outcount", outcount);
                json = JSONObject.fromObject(requestmap);
        	}
        	
        }
        
        
        
        if (type == 1) {

            Videostatistic videostatisticday = videostatisticService.findvideostatisicfortype("day");
            if (videostatisticday != null) {
                Information = videostatisticday.getDateInformation();
                json = JSONObject.fromObject(Information);
                   
            } else {
                List<String> day = new ArrayList<String>();
                List<String> incount = new ArrayList<String>();
                List<String> outcount = new ArrayList<String>();
                Date date1 = new Date();
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(date1);
                int Number = calendar1.get(Calendar.HOUR_OF_DAY);

                for (int r = 1; r <= Number; r++) {
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
                    Videostatistic addvideostatistic = new Videostatistic();
                    addvideostatistic.setDateType("day");
                    addvideostatistic.setDateInformation(json.toString());
                    addvideostatistic.setCreateTime(calendar.getTime());
                    videostatisticService.save(addvideostatistic);
                    
                    incount.add(String.valueOf(innum));
                    outcount.add(String.valueOf(outnum));
                    day.add(String.valueOf(r));
                }

                requestmap.put("day", day);
                requestmap.put("incount", incount);
                requestmap.put("outcount", outcount);
                json = JSONObject.fromObject(requestmap);
                /*Videostatistic addvideostatistic = new Videostatistic();
                addvideostatistic.setDateType("day");
                addvideostatistic.setDateInformation(json.toString());
                addvideostatistic.setCreateTime(new Date());
                videostatisticService.save(addvideostatistic);*/
            }

        }


        /**
         * 按照周来统计
         */


        if (type == 2) {

            Videostatistic videostatisticweek = videostatisticService.findvideostatisicfortype("week");
            if (videostatisticweek != null) {

                Information = videostatisticweek.getDateInformation();
                json = JSONObject.fromObject(Information);
            } else {
                List<String> day = new ArrayList<String>();
                List<String> incount = new ArrayList<String>();
                List<String> outcount = new ArrayList<String>();

                SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd");
    	


                int c = 0;
                for (int r = 0; r < 7; r++) {

                    Calendar cal = Calendar.getInstance();

                    cal.set(cal.DAY_OF_WEEK, cal.MONDAY);
                    String weekhand = simdf.format(cal.getTime());
                    int begin = cal.get(Calendar.DATE);

                    Calendar begincal = Calendar.getInstance();
                    begincal.setTime(cal.getTime());


                    begincal.set(Calendar.DATE, begincal.get(Calendar.DATE) + c);
                    Map<String, Integer> cMap = VideoStatistic.startFindNumberStatrewrite(begincal.getTime(), begincal.getTime(), 2);
                    Integer innum = cMap.get("IN");
                    Integer outnum = cMap.get("OUT");
                    json = JSONObject.fromObject(cMap);
                    Videostatistic addvideostatistic = new Videostatistic();
                    addvideostatistic.setDateType("week");
                    addvideostatistic.setDateInformation(json.toString());
                    addvideostatistic.setCreateTime(begincal.getTime());
                    videostatisticService.save(addvideostatistic);
                    
                    incount.add(String.valueOf(innum));
                    outcount.add(String.valueOf(outnum));
                    day.add(String.valueOf(begin + c));
                    c++;
                }

                requestmap.put("day", day);
                requestmap.put("incount", incount);
                requestmap.put("outcount", outcount);
                json = JSONObject.fromObject(requestmap);
                /*Videostatistic addvideostatistic = new Videostatistic();
                addvideostatistic.setDateType("week");
                addvideostatistic.setDateInformation(json.toString());
                addvideostatistic.setCreateTime(new Date());
                videostatisticService.save(addvideostatistic);*/


            }
        }


        /**
         * 按照年来统计
         */


        if (type == 3) {

            Videostatistic videostatisticyear = videostatisticService.findvideostatisicfortype("year");

            if (videostatisticyear != null) {

                Information = videostatisticyear.getDateInformation();
                json = JSONObject.fromObject(Information);
            } else {
                List<String> day = new ArrayList<String>();
                List<String> incount = new ArrayList<String>();
                List<String> outcount = new ArrayList<String>();

                SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd");


                int c = 0;
                for (int r = 1; r <= 12; r++) {

                    Calendar cal = Calendar.getInstance();
                    Date date = new Date();
                    cal.setTime(date);
                    int YEAR = cal.get(Calendar.YEAR);
                    String yearbegindateStr = YEAR + "-" + "01" + "-" + "01";
                    Date yearbegindate = simdf.parse(yearbegindateStr);
                    cal.setTime(yearbegindate);


                    cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + c);
                    //cal.add(Calendar.MONTH,  + c);
                    Map<String, Integer> cMap = VideoStatistic.startFindNumberStatrewrite(cal.getTime(), cal.getTime(), 4);
                    Integer innum = cMap.get("IN");
                    Integer outnum = cMap.get("OUT");
                    json = JSONObject.fromObject(cMap);
                    Videostatistic addvideostatistic = new Videostatistic();
                    addvideostatistic.setDateType("year");
                    addvideostatistic.setDateInformation(json.toString());
                    addvideostatistic.setCreateTime(cal.getTime());
                    videostatisticService.save(addvideostatistic);
                    
                    
                    incount.add(String.valueOf(innum));
                    outcount.add(String.valueOf(outnum));
                    day.add(String.valueOf(r));
                    c++;
                }

                requestmap.put("day", day);
                requestmap.put("incount", incount);
                requestmap.put("outcount", outcount);
                json = JSONObject.fromObject(requestmap);
                /*Videostatistic addvideostatistic = new Videostatistic();
                addvideostatistic.setDateType("year");
                addvideostatistic.setDateInformation(json.toString());
                addvideostatistic.setCreateTime(new Date());
                videostatisticService.save(addvideostatistic);*/


            }
        }


        return R.ok(json);

    }


    @RequestMapping("/init")
    public R customerCountStatistical() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        Map<String, Object> map = statisticalService.statisticalInit(calendar.getTime());
        return R.ok(map);
    }

    @GetMapping("/gender")
    public R genderStatistical() {
        return R.ok(statisticalService.statisticalGender());
    }

    /**
     * 0~12
     * 13~18
     * 19~30
     * 31~50
     * 51~70
     * >70
     *
     * @return
     */
    @GetMapping("/age")
    public R ageStatistical() {
        return R.ok(statisticalService.statisticalAge()[0]);
    }

    @GetMapping("/team")
    public R teamStatistical(@RequestParam(value = "tagId") Integer tagId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        List<Map<String, Object>> objectList = statisticalService.statisticalTeam(calendar.getTime(), tagId);

        return R.ok(objectList);
    }

    @GetMapping("/day")
    public R dayStatistical(String cc) throws ParseException {

        Calendar calendar = Calendar.getInstance();
        if (cc != "") {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(cc);
            calendar.setTime(date);
        } else {
            calendar.setTime(new Date());
        }
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);

        // calendar.set(Calendar.HOUR,0);                //原来代码    用的是12小时制-----坑

        calendar.set(Calendar.HOUR_OF_DAY, 0);

        Date toDay = calendar.getTime();

        calendar.add(Calendar.DATE, 1);

        List<Map<String, Object>> oldObjects = statisticalService.statisticalOldPassengerFlowByDay(toDay, calendar.getTime());

        List<Map<String, Object>> newObjects = statisticalService.statisticalNewPassengerFlowByDay(toDay, calendar.getTime());

        List<String> oldHours = oldObjects.stream().map(map -> map.get("hour").toString()).collect(Collectors.toList());

        List<String> oldCounts = oldObjects.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        List<String> newHours = newObjects.stream().map(map -> map.get("hour").toString()).collect(Collectors.toList());

        List<String> newCounts = newObjects.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        return R.ok(convert(oldHours, oldCounts, newHours, newCounts));
    }


    public Map<String, Object> convert(List<String> oldTime, List<String> oldCounts, List<String> newTime, List<String> newCounts) {
        Map<String, Object> oldMap = new HashMap<>();
        oldMap.put("times", BeanUtils.CollStringToIntegerLst(oldTime));
        oldMap.put("counts", BeanUtils.CollStringToIntegerLst(oldCounts));

        Map<String, Object> newMap = new HashMap<>();
        newMap.put("times", BeanUtils.CollStringToIntegerLst(newTime));
        newMap.put("counts", BeanUtils.CollStringToIntegerLst(newCounts));

        Map<String, Object> map = new HashMap<>();

        map.put("old", oldMap);
        map.put("new", newMap);
        return map;
    }

    @GetMapping("/week")
    public R weekStatistical() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);


        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Date first = calendar.getTime();

        calendar.add(Calendar.DATE, 6);
        Date last = calendar.getTime();

        List<Map<String, Object>> objects = statisticalService.statisticalOldPassengerFlowByWeek(first, last);
        List<Map<String, Object>> maps = statisticalService.statisticalNewPassengerFlowByWeek(first, last);

        List<String> oldDays = objects.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> oldCounts = objects.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        List<String> newDays = maps.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> newCounts = maps.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("old", weekConvert(BeanUtils.CollStringToIntegerLst(oldDays), BeanUtils.CollStringToIntegerLst(oldCounts), first));
        map.put("new", weekConvert(BeanUtils.CollStringToIntegerLst(newDays), BeanUtils.CollStringToIntegerLst(newCounts), first));

        return R.ok(map);
    }

    @GetMapping("/yesterweek")
    public R yesterweekStatistical() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.add(Calendar.DATE, -8);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Date first = calendar.getTime();

        calendar.add(Calendar.DATE, 6);
        Date last = calendar.getTime();

        List<Map<String, Object>> objects = statisticalService.statisticalOldPassengerFlowByWeek(first, last);

        List<Map<String, Object>> maps = statisticalService.statisticalNewPassengerFlowByWeek(first, last);

        List<String> oldDays = objects.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> oldCounts = objects.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        List<String> newDays = maps.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> newCounts = maps.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("old", weekConvert(BeanUtils.CollStringToIntegerLst(oldDays), BeanUtils.CollStringToIntegerLst(oldCounts), first));
        map.put("new", weekConvert(BeanUtils.CollStringToIntegerLst(newDays), BeanUtils.CollStringToIntegerLst(newCounts), first));

        return R.ok(map);
    }


    public Map<String, Object> weekConvert(List<Integer> days, List<Integer> counts, Date first) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(first);
        List<Integer> dayList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            if (i != 1) {
                calendar.add(Calendar.DATE, 1);
            }
            String time = simpleDateFormat.format(calendar.getTime());
            Integer timeInt = Integer.parseInt(time);
            if (days.contains(timeInt)) {

                dayList.add(days.get(days.indexOf(timeInt)));
                countList.add(counts.get(days.indexOf(timeInt)));
            } else {
                dayList.add(Integer.parseInt(time));
                countList.add(0);

            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("times", dayList);
        map.put("counts", countList);
        return map;

    }

    @GetMapping("/month")
    public R monthStatistical() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date currentMonth = calendar.getTime();

        calendar.add(Calendar.MONTH, 1);
        Date nextMonth = calendar.getTime();

        List<Map<String, Object>> objects = statisticalService.statisticalOldPassengerFlowByMonth(currentMonth, nextMonth);

        List<Map<String, Object>> maps = statisticalService.statisticalNewPassengerFlowByMonth(currentMonth, nextMonth);

        List<String> oldDays = objects.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> oldCounts = objects.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        List<String> newDays = maps.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> newCounts = maps.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("old", monthConvert(BeanUtils.CollStringToIntegerLst(oldDays), BeanUtils.CollStringToIntegerLst(oldCounts)));
        map.put("new", monthConvert(BeanUtils.CollStringToIntegerLst(newDays), BeanUtils.CollStringToIntegerLst(newCounts)));

        return R.ok(map);
    }


    @GetMapping("/newmonth")
    public R newmonthStatistical(String date) throws ParseException {


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        Date dat3 = formatter2.parse(date);           //将拼起来的String  转成Date  上面的格式
        calendar.setTime(dat3);

       /* Date date1=new Date();  //获取当前时间
        //获取当前时间的年份
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy");
        //将当前时间转成String
        String year = formatter.format(date1);
        //将年份与传来的日期拼接起来    传来的是  例如  09-01
        String da   =year+"-"+date;
        SystemParameter.out.println(da);
        SimpleDateFormat formatter2 = new SimpleDateFormat( "yyyy-MM-dd");
        Date dat3 = formatter2.parse(da);           //将拼起来的String  转成Date  上面的格式
        calendar.setTime(dat3);*/


        System.out.println(Calendar.YEAR);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);


        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date currentMonth = calendar.getTime();

        calendar.add(Calendar.MONTH, 1);
        Date nextMonth = calendar.getTime();

        List<Map<String, Object>> objects = statisticalService.statisticalOldPassengerFlowByMonthCustomer(currentMonth, nextMonth);

        List<Map<String, Object>> maps = statisticalService.statisticalNewPassengerFlowByMonth(currentMonth, nextMonth);

        List<String> oldDays = objects.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> oldCounts = objects.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        List<String> newDays = maps.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> newCounts = maps.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("old", newmonthConvert(BeanUtils.CollStringToIntegerLst(oldDays), BeanUtils.CollStringToIntegerLst(oldCounts), dat3));
        map.put("new", newmonthConvert(BeanUtils.CollStringToIntegerLst(newDays), BeanUtils.CollStringToIntegerLst(newCounts), dat3));

        return R.ok(map);
    }

    public Map<String, Object> monthConvert(List<Integer> days, List<Integer> counts) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());

        int dayNumber = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);

        List<Integer> dayList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for (int i = 1; i <= dayNumber; i++) {

            if (days.contains(i)) {
                dayList.add(days.get(days.indexOf(i)));
                countList.add(counts.get(days.indexOf(i)));
            } else {

                dayList.add(i);
                countList.add(0);
            }

        }
        Map<String, Object> map = new HashMap<>();
        map.put("times", dayList);
        map.put("counts", countList);
        return map;

    }

    public Map<String, Object> newmonthConvert(List<Integer> days, List<Integer> counts, Date date) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        int dayNumber = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);

        List<Integer> dayList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for (int i = 1; i <= dayNumber; i++) {

            if (days.contains(i)) {
                dayList.add(days.get(days.indexOf(i)));
                countList.add(counts.get(days.indexOf(i)));
            } else {

                dayList.add(i);
                countList.add(0);
            }

        }
        Map<String, Object> map = new HashMap<>();
        map.put("times", dayList);
        map.put("counts", countList);
        return map;
    }


    @GetMapping("/year")
    public R yearStatistical() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        calendar.set(Calendar.DAY_OF_YEAR, 1);
        Date currentMonth = calendar.getTime();

        calendar.add(Calendar.YEAR, 1);
        Date nextMonth = calendar.getTime();


        List<Map<String, Object>> objects = statisticalService.statisticalOldPassengerFlowByMonthER(currentMonth, nextMonth);

        List<Map<String, Object>> maps = statisticalService.statisticalNewPassengerFlowByMonthER(currentMonth, nextMonth);


        List<String> oldDays = objects.stream().map(map -> map.get("mon").toString()).collect(Collectors.toList());

        List<String> oldCounts = objects.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        List<String> newDays = maps.stream().map(map -> map.get("mon").toString()).collect(Collectors.toList());

        List<String> newCounts = maps.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("old", yearConvert(BeanUtils.CollStringToIntegerLst(oldDays), BeanUtils.CollStringToIntegerLst(oldCounts)));
        map.put("new", yearConvert(BeanUtils.CollStringToIntegerLst(newDays), BeanUtils.CollStringToIntegerLst(newCounts)));

        return R.ok(map);
    }


    @GetMapping("/newyear")
    public R newyearStatistical() {                         //新添加的统计上一年的
        Calendar calendar = Calendar.getInstance();


        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.YEAR, -1);
        Date currentMonth = calendar.getTime();

        calendar.add(Calendar.YEAR, 1);
        Date nextMonth = calendar.getTime();


        List<Map<String, Object>> objects = statisticalService.statisticalOldPassengerFlowByMonthER(currentMonth, nextMonth);

        List<Map<String, Object>> maps = statisticalService.statisticalNewPassengerFlowByMonthER(currentMonth, nextMonth);


        List<String> oldDays = objects.stream().map(map -> map.get("mon").toString()).collect(Collectors.toList());

        List<String> oldCounts = objects.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        List<String> newDays = maps.stream().map(map -> map.get("mon").toString()).collect(Collectors.toList());

        List<String> newCounts = maps.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("old", yearConvert(BeanUtils.CollStringToIntegerLst(oldDays), BeanUtils.CollStringToIntegerLst(oldCounts)));
        map.put("new", yearConvert(BeanUtils.CollStringToIntegerLst(newDays), BeanUtils.CollStringToIntegerLst(newCounts)));

        return R.ok(map);
    }


    public Map<String, Object> yearConvert(List<Integer> days, List<Integer> counts) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());

//        int dayNumber = rightNow.getActualMaximum(Calendar.WEEK_OF_YEAR);
        int dayNumber = 12;

//      int dayNumber = rightNow.getActualMaximum(Calendar.DAY_OF_YEAR);
        List<Integer> dayList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for (int i = 1; i <= dayNumber; i++) {

            if (days.contains(i)) {
                dayList.add(days.get(days.indexOf(i)));
                countList.add(counts.get(days.indexOf(i)));
            } else {

                dayList.add(i);
                countList.add(0);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("times", dayList);
        map.put("counts", countList);
        return map;

    }

    @GetMapping("/passengerFlowCount")
    public R passengerFlowCountStatistical() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);

        Map<String, Object> map = statisticalService.statisticalPassengerFlowCount(calendar.getTime());
        return R.ok(map);
    }

    @GetMapping("/genderCount")
    public R genderCountStatistical() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);

        List<Map<String, Object>> maps = statisticalService.statisticalGenderCount(calendar.getTime());


        Map<String, Object> map = new HashMap<>();
        map.put("old", maps.get(1));
        map.put("new", maps.get(0));

        return R.ok(map);
    }

    @GetMapping("/ageCount")
    public R ageCountStatistical() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);

        List<Map<String, Object>> maps = statisticalService.statisticalAgeCount(calendar.getTime());

        Map<String, Object> map = new HashMap<>();
        map.put("old", maps.get(1));
        map.put("new", maps.get(0));

        return R.ok(map);
    }


}
