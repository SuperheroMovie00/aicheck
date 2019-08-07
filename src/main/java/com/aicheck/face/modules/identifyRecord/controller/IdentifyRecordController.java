/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.identifyRecord.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aicheck.face.common.constant.ResultEnum;
import com.aicheck.face.modules.customer.entity.Customer;
import com.aicheck.face.modules.customer.service.CustomerService;
import com.aicheck.face.modules.customer.utils.ConverterUtils;
import com.aicheck.face.modules.customer.vo.CustomerVO;
import com.aicheck.face.modules.device.constants.DeviceEnum;
import com.aicheck.face.modules.device.entity.Device;
import com.aicheck.face.modules.device.service.DeviceService;
import com.aicheck.face.modules.identifyRecord.entity.IdentifyRecord;
import com.aicheck.face.modules.identifyRecord.service.IdentifyRecordService;
import com.aicheck.face.modules.identifyRecord.vo.IdentifyRecordVO;
import com.aicheck.face.modules.nettyPush.GlobalUser;
import com.aicheck.face.modules.nettyPush.Message;
import com.aicheck.face.modules.nettyPush.MessageTypeEnum;
import com.aicheck.face.modules.visitorsRecord.entity.VisitorsRecord;
import com.aicheck.face.modules.visitorsRecord.service.VisitorsRecordService;
import com.aicheck.face.vo.R;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 10:48 AM 2018/11/23
 */
@RestController
@RequestMapping("/v1/identify-record")
@Slf4j
public class IdentifyRecordController {
    @Autowired
    private IdentifyRecordService identifyRecordService;
    @Autowired
    private VisitorsRecordService visitorsRecordService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public R findAllList(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage, @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize) {
        Page<IdentifyRecord> page = identifyRecordService.findAllList(currentPage,pageSize);
        if(page==null){
            return R.error("/v1/identify-record/get=>page为空");
        }

        return R.ok(convert(page.getContent()));
    }

    @PostMapping("/notify-list")
    public R findNotifyList(@RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize) {

        Page<IdentifyRecord> page = identifyRecordService.findByNotifyList(pageSize);
        if(page==null){
            return R.error("/v1/identify-record/notify-list=>page为空");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("identifyRecords",convert(page.getContent()));

        return R.ok(map);
    }

    @PostMapping("/latest")
    public R findLatestIdentify() {
        log.info("/identify-record/latest 识别数据后五秒请求");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.roll(Calendar.SECOND, - 5);
        System.out.println(simpleDateFormat.format(calendar.getTime()) + "----" + simpleDateFormat.format(date));

        List<IdentifyRecord> identifyRecords = identifyRecordService.findByCreateTimeBefore(calendar.getTime());
        if(identifyRecords==null){
            return R.error("/v1/identify-record/latest=>identifyRecords为空");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("identifyRecords",convert(identifyRecords));
        log.info("/identify-record/latest 识别数据后五秒返回：{}",JSON.toJSONString(map));
        return R.ok(map);
    }

    @PostMapping("/latest-all")
    public R findLatestIdentifyAll() {

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.roll(Calendar.SECOND, - 10);

        IdentifyRecord identifyRecord = identifyRecordService.findByLatestOrderByCreateTime(calendar.getTime());

        VisitorsRecord visitorsRecord = visitorsRecordService.findLatestOrderByCreateTime(calendar.getTime());

        if (identifyRecord == null && visitorsRecord == null) {
            return R.ok();
        }

        if (identifyRecord == null) {
            log.info("广告分类播放请求成功  -> {} ",visitorsRecord.getGender());
            return R.ok(visitorsRecord);
        }

        if (visitorsRecord == null) {
            Customer customer = customerService.findById(Integer.parseInt(identifyRecord.getCustomerId()));
            log.info("广告分类播放请求成功  -> {} ",customer.getGender());
            return R.ok(ConverterUtils.converterCustomerVO(customer));
        }


        Customer customer = customerService.findById(Integer.parseInt(identifyRecord.getCustomerId()));

        long identifyTimestamp = identifyRecord.getCreateTime().getTime();

        long visitorsTimestamp = visitorsRecord.getCreateTime().getTime();

        if (visitorsTimestamp > identifyTimestamp) {
            log.info("广告分类播放请求成功  -> {} ",visitorsRecord.getGender());
            return R.ok(visitorsRecord);
        }

        log.info("广告分类播放请求成功  -> {} ",customer.getGender());

        return R.ok(ConverterUtils.converterCustomerVO(customer));
    }

    @GetMapping("/{id}")
    public R findById(@PathVariable Integer id) {
        IdentifyRecord identifyRecord = identifyRecordService.findById(id);
        if (identifyRecord == null) {
            return R.error(ResultEnum.IS_NOT_EXIST.getCode(),ResultEnum.IS_NOT_EXIST.getMsg());
        }

        Customer customer = customerService.findById(Integer.parseInt(identifyRecord.getCustomerId()));
        CustomerVO customerVO = new CustomerVO();
        if (customer != null) {
            BeanUtils.copyProperties(customer,customerVO);
            customerVO.setCustomerId(String.valueOf(customer.getId()));
            customerVO.setCustomerName(customer.getName());
        }

        IdentifyRecordVO identifyRecordVO = new IdentifyRecordVO();
        identifyRecordVO.setCustomerVO(customerVO);
        BeanUtils.copyProperties(identifyRecord,identifyRecordVO);
        return R.ok(identifyRecordVO);
    }

    @GetMapping("/face-coordinates")
    public R findFaceCoordinates(@RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime) {

        List<String> list = identifyRecordService.findByFaceCoordinates(startTime,endTime);

        if (CollectionUtils.isEmpty(list)) {
            return R.ok(new ArrayList<>());
        }
        List<Map<String,Double>> centers = new ArrayList<>();
        for (String s : list ) {
            JSONObject jsonObject = null;
            try {
                jsonObject = JSONObject.parseObject(s);
            } catch (Exception e) {
                continue;
            }

            if (jsonObject == null) {
                continue;
            }

            String center = jsonObject.getString("center");

            String[] xy = center.split(",");

            if (xy.length < 2) {
                continue;
            }

            double x = Double.valueOf(xy[0]);

            double y = Double.valueOf(xy[1]);

            Map<String,Double> map = new HashMap<>();
            map.put("x",x);
            map.put("y",y);

            centers.add(map);

        }

        return R.ok(centers);
    }

    @PostMapping
    public R save(@RequestBody String customerId) {
        log.info("识别到老客:{}",customerId);
        JSONObject jsonObject = JSON.parseObject(customerId);
        IdentifyRecord identifyRecord = new IdentifyRecord();
        identifyRecord.setFaceCoordinates(jsonObject.getString("faceCoordinates"));
        identifyRecord.setCustomerId(jsonObject.getString("customerId"));
        identifyRecord = identifyRecordService.save(identifyRecord);
        Customer customer = customerService.findById(Integer.parseInt(jsonObject.getString("customerId")));
        if(customer==null){
            return R.error("/v1/identify-record/post=>customer为空");
        }

        CustomerVO customerVO = new CustomerVO();
        BeanUtils.copyProperties(customer,customerVO);
        customerVO.setCustomerId(String.valueOf(customer.getId()));
        customerVO.setCustomerName(customer.getName());
        Map<String,Object> map = new HashMap<>();
        map.put("customer",customerVO);
        map.put("createTime",identifyRecord.getCreateTime());

        // 推送至手机
        List<Device> deviceList = deviceService.findByPlatform(DeviceEnum.MOBILE.getValue());
        if(deviceList==null){
            return R.error("/v1/identify-record/post=>deviceList为空");
        }

        List<String> ipList = deviceList.stream().map(Device::getIpAddress).collect(Collectors.toList());
        GlobalUser.channels.forEach(channel -> {
            if (ipList.contains(channel.remoteAddress().toString())) {
                Message message = new Message();
                message.setAction(MessageTypeEnum.OLD_CUSTOMER.getValue());
                message.setObject(customerVO);
                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
            }
        });

        return R.ok(map);
    }

    private List<IdentifyRecordVO> convert(List<IdentifyRecord> identifyRecords) {
        List<IdentifyRecordVO> identifyRecordVOS = new ArrayList<>();
        if (identifyRecords != null && identifyRecords.size() > 0) {
            for (IdentifyRecord identifyRecord : identifyRecords) {
                IdentifyRecordVO identifyRecordVO = new IdentifyRecordVO();
                BeanUtils.copyProperties(identifyRecord,identifyRecordVO);
                if (StringUtils.isEmpty(identifyRecord.getCustomerId())) {
                    continue;
                }
                Customer customer = customerService.findById(Integer.parseInt(identifyRecord.getCustomerId()));
                if (customer != null) {
                    CustomerVO customerVO = new CustomerVO();
                    BeanUtils.copyProperties(customer,customerVO);
                    customerVO.setCustomerId(String.valueOf(customer.getId()));
                    identifyRecordVO.setCustomerVO(customerVO);

                }
                identifyRecordVOS.add(identifyRecordVO);
            }
        }
        return identifyRecordVOS;
    }

}
