/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.statistical.controller;

import com.aicheck.face.common.utils.BeanUtils;
import com.aicheck.face.modules.statistical.service.StatisticalService;
import com.aicheck.face.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
   

    @RequestMapping("/init")
    public R customerCountStatistical() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR,0);
        Map<String,Object> map = statisticalService.statisticalInit(calendar.getTime());
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
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR,0);
        List<Map<String,Object>> objectList =  statisticalService.statisticalTeam(calendar.getTime(),tagId);

        return R.ok(objectList);
    }

    @GetMapping("/day")
    public R dayStatistical(String cc) throws ParseException {

        Calendar calendar = Calendar.getInstance();
        if(cc!=""){
            SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
            Date date = formatter.parse(cc);
            calendar.setTime(date);
        }else{
            calendar.setTime(new Date());
        }
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);

        //      calendar.set(Calendar.HOUR,0);                //原来代码    用的是12小时制-----坑

        calendar.set(Calendar.HOUR_OF_DAY, 0);

        Date toDay = calendar.getTime();

        calendar.add(Calendar.DATE,  1);

        List<Map<String,Object>> oldObjects = statisticalService.statisticalOldPassengerFlowByDay(toDay,calendar.getTime());

        List<Map<String,Object>> newObjects = statisticalService.statisticalNewPassengerFlowByDay(toDay,calendar.getTime());

        List<String> oldHours = oldObjects.stream().map(map -> map.get("hour").toString()).collect(Collectors.toList());

        List<String> oldCounts = oldObjects.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        List<String> newHours = newObjects.stream().map(map -> map.get("hour").toString()).collect(Collectors.toList());

        List<String> newCounts = newObjects.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        return R.ok(convert(oldHours,oldCounts,newHours,newCounts));
    }



    public Map<String,Object> convert(List<String> oldTime,List<String> oldCounts,List<String> newTime,List<String> newCounts) {
        Map<String,Object> oldMap = new HashMap<>();
        oldMap.put("times",BeanUtils.CollStringToIntegerLst(oldTime));
        oldMap.put("counts",BeanUtils.CollStringToIntegerLst(oldCounts));

        Map<String,Object> newMap = new HashMap<>();
        newMap.put("times",BeanUtils.CollStringToIntegerLst(newTime));
        newMap.put("counts",BeanUtils.CollStringToIntegerLst(newCounts));

        Map<String,Object> map = new HashMap<>();

        map.put("old",oldMap);
        map.put("new",newMap);
        return map;
    }

    @GetMapping("/week")
    public R weekStatistical() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);


        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Date first = calendar.getTime();

        calendar.add(Calendar.DATE,6);
        Date last = calendar.getTime();

        List<Map<String,Object>> objects = statisticalService.statisticalOldPassengerFlowByWeek(first,last);
        List<Map<String,Object>> maps = statisticalService.statisticalNewPassengerFlowByWeek(first,last);

        List<String> oldDays = objects.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> oldCounts = objects.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        List<String> newDays = maps.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> newCounts = maps.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        Map<String,Object> map = new HashMap<>();
        map.put("old",weekConvert(BeanUtils.CollStringToIntegerLst(oldDays),BeanUtils.CollStringToIntegerLst(oldCounts),first));
        map.put("new",weekConvert(BeanUtils.CollStringToIntegerLst(newDays),BeanUtils.CollStringToIntegerLst(newCounts),first));

        return R.ok(map);
    }

    @GetMapping("/yesterweek")
    public R yesterweekStatistical() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.add(Calendar.DATE,  -8);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Date first = calendar.getTime();

        calendar.add(Calendar.DATE,6);
        Date last = calendar.getTime();

        List<Map<String,Object>> objects = statisticalService.statisticalOldPassengerFlowByWeek(first,last);
        List<Map<String,Object>> maps = statisticalService.statisticalNewPassengerFlowByWeek(first,last);

        List<String> oldDays = objects.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> oldCounts = objects.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        List<String> newDays = maps.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> newCounts = maps.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        Map<String,Object> map = new HashMap<>();
        map.put("old",weekConvert(BeanUtils.CollStringToIntegerLst(oldDays),BeanUtils.CollStringToIntegerLst(oldCounts),first));
        map.put("new",weekConvert(BeanUtils.CollStringToIntegerLst(newDays),BeanUtils.CollStringToIntegerLst(newCounts),first));

        return R.ok(map);
    }



    public Map<String,Object> weekConvert(List<Integer> days,List<Integer> counts,Date first) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(first);
        List<Integer> dayList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();

        for(int i = 1;i <= 7;i++) {
            if (i != 1) {
                calendar.add(Calendar.DATE,1);
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

        Map<String,Object> map = new HashMap<>();
        map.put("times",dayList);
        map.put("counts",countList);
        return map;

    }

    @GetMapping("/month")
    public R monthStatistical() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        Date currentMonth = calendar.getTime();

        calendar.add(Calendar.MONTH,1);
        Date nextMonth = calendar.getTime();

        List<Map<String,Object>> objects = statisticalService.statisticalOldPassengerFlowByMonth(currentMonth,nextMonth);

        List<Map<String,Object>> maps = statisticalService.statisticalNewPassengerFlowByMonth(currentMonth,nextMonth);

        List<String> oldDays = objects.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> oldCounts = objects.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        List<String> newDays = maps.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> newCounts = maps.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        Map<String,Object> map = new HashMap<>();
        map.put("old",monthConvert(BeanUtils.CollStringToIntegerLst(oldDays),BeanUtils.CollStringToIntegerLst(oldCounts)));
        map.put("new",monthConvert(BeanUtils.CollStringToIntegerLst(newDays),BeanUtils.CollStringToIntegerLst(newCounts)));

        return R.ok(map);
    }


    @GetMapping("/newmonth")
    public R newmonthStatistical(String date) throws ParseException {



        Calendar calendar = Calendar.getInstance();


        Date date1=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy");
        String year = formatter.format(date1);
        String da=year+"-"+date;
        System.out.println(da);
        SimpleDateFormat formatter2 = new SimpleDateFormat( "yyyy-MM-dd");
        Date dat3 = formatter.parse(da);
        calendar.setTime(dat3);


        System.out.println(Calendar.YEAR);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);


        calendar.set(Calendar.DAY_OF_MONTH,1);
        Date currentMonth = calendar.getTime();

        calendar.add(Calendar.MONTH,1);
        Date nextMonth = calendar.getTime();

        List<Map<String,Object>> objects = statisticalService.statisticalOldPassengerFlowByMonth(currentMonth,nextMonth);

        List<Map<String,Object>> maps = statisticalService.statisticalNewPassengerFlowByMonth(currentMonth,nextMonth);

        List<String> oldDays = objects.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> oldCounts = objects.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        List<String> newDays = maps.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> newCounts = maps.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        Map<String,Object> map = new HashMap<>();
        map.put("old",monthConvert(BeanUtils.CollStringToIntegerLst(oldDays),BeanUtils.CollStringToIntegerLst(oldCounts)));
        map.put("new",monthConvert(BeanUtils.CollStringToIntegerLst(newDays),BeanUtils.CollStringToIntegerLst(newCounts)));

        return R.ok(map);
    }

    public Map<String,Object> monthConvert(List<Integer> days,List<Integer> counts) {
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(new Date());

            int dayNumber = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);

            List<Integer> dayList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for(int i = 1;i <= dayNumber;i++) {

            if (days.contains(i)) {
                dayList.add(days.get(days.indexOf(i)));
                countList.add(counts.get(days.indexOf(i)));
            } else {

                dayList.add(i);
                countList.add(0);
            }

        }
        Map<String,Object> map = new HashMap<>();
        map.put("times",dayList);
        map.put("counts",countList);
        return map;

    }

    public Map<String,Object> newmonthConvert(List<Integer> days,List<Integer> counts,Date date) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        int dayNumber = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);

        List<Integer> dayList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for(int i = 1;i <= dayNumber;i++) {

            if (days.contains(i)) {
                dayList.add(days.get(days.indexOf(i)));
                countList.add(counts.get(days.indexOf(i)));
            } else {

                dayList.add(i);
                countList.add(0);
            }

        }
        Map<String,Object> map = new HashMap<>();
        map.put("times",dayList);
        map.put("counts",countList);
        return map;
    }




    @GetMapping("/year")
    public R yearStatistical() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        calendar.set(Calendar.DAY_OF_YEAR,1);
        Date currentMonth = calendar.getTime();

        calendar.add(Calendar.YEAR,1);
        Date nextMonth = calendar.getTime();

        List<Map<String,Object>> objects = statisticalService.statisticalOldPassengerFlowByMonth(currentMonth,nextMonth);

        List<Map<String,Object>> maps = statisticalService.statisticalNewPassengerFlowByMonth(currentMonth,nextMonth);

        List<String> oldDays = objects.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> oldCounts = objects.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        List<String> newDays = maps.stream().map(map -> map.get("day").toString()).collect(Collectors.toList());

        List<String> newCounts = maps.stream().map(map -> map.get("count").toString()).collect(Collectors.toList());

        Map<String,Object> map = new HashMap<>();
        map.put("old",yearConvert(BeanUtils.CollStringToIntegerLst(oldDays),BeanUtils.CollStringToIntegerLst(oldCounts)));
        map.put("new",yearConvert(BeanUtils.CollStringToIntegerLst(newDays),BeanUtils.CollStringToIntegerLst(newCounts)));

        return R.ok(map);
    }

    public Map<String,Object> yearConvert(List<Integer> days,List<Integer> counts) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());

        int dayNumber = rightNow.getActualMinimum(Calendar.DAY_OF_YEAR);
//      int dayNumber = rightNow.getActualMaximum(Calendar.DAY_OF_YEAR);
        List<Integer> dayList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for(int i = 1;i <= dayNumber;i++) {

            if (days.contains(i)) {
                dayList.add(days.get(days.indexOf(i)));
                countList.add(counts.get(days.indexOf(i)));
            } else {

                dayList.add(i);
                countList.add(0);
            }

        }
        Map<String,Object> map = new HashMap<>();
        map.put("times",dayList);
        map.put("counts",countList);
        return map;

    }

    @GetMapping("/passengerFlowCount")
    public R passengerFlowCountStatistical() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR,0);

        Map<String,Object> map = statisticalService.statisticalPassengerFlowCount(calendar.getTime());
        return R.ok(map);
    }
    @GetMapping("/genderCount")
    public R genderCountStatistical() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR,0);

        List<Map<String,Object>> maps = statisticalService.statisticalGenderCount(calendar.getTime());
       
 
        Map<String,Object> map = new HashMap<>();
        map.put("old",maps.get(1));
        map.put("new",maps.get(0));

        return R.ok(map);
    }	
    @GetMapping("/ageCount")
    public R ageCountStatistical() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR,0);

        List<Map<String,Object>> maps = statisticalService.statisticalAgeCount(calendar.getTime());

        Map<String,Object> map = new HashMap<>();
        map.put("old",maps.get(1));
        map.put("new",maps.get(0));

        return R.ok(map);
    }


}
