/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.device.controller;

import com.alibaba.fastjson.JSON;
import com.aicheck.face.common.constant.ResultEnum;
import com.aicheck.face.modules.device.entity.Device;
import com.aicheck.face.modules.device.entity.DeviceForm;
import com.aicheck.face.modules.device.service.DeviceService;
import com.aicheck.face.modules.nettyPush.GlobalUser;
import com.aicheck.face.modules.nettyPush.Message;
import com.aicheck.face.vo.R;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:19 PM 2019/1/29
 */
@RestController
@RequestMapping("/v1/device")
@Slf4j
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public R findAll(@RequestParam(value = "currentPage",defaultValue = "1")Integer currentPage,
                     @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize,@RequestParam(value = "platform",required = false) String platform) {

        Page<Device> page = deviceService.findAll(currentPage,pageSize,platform);
        if(page==null){
            return R.error("get device=> 设备集合新消息为空");
        }
        return R.ok(page.getContent()).put("total", page.getTotalElements());
    }

    @GetMapping("/not-group")
    public R findByNotGroupDevice() {

        List<Device> deviceList = deviceService.findByNotGroupDevice();
        if(deviceList==null){
            return R.error("not-group=> 设备集合为空");
        }

        return R.ok(deviceList);
    }
    
    
    //根据平台为box 并且父id为0（没有父id）
    @GetMapping("/querydevicefrobox")
    public R querydevicefrobox(String deviceip) {
    	List<Device> devices=deviceService.querydevicefrobox(deviceip);
        if(devices==null){
            return R.error("querydevicefrobox=> 设备集合新消息为空");
        }
    	return R.ok(devices);
    }
    
    
    //根据id查询一个系统设置的信息
    @GetMapping("/ids")
    public R findById1(@RequestParam(value = "ids") Integer ids) {
    	
    	Device device=deviceService.findById(ids);
    	if (device == null) {
			return R.error(ResultEnum.IS_NOT_EXIST.getCode(), ResultEnum.IS_NOT_EXIST.getMsg());
		}
    	return R.ok(device);	
    }
    
    
    @PostMapping("/ccc")
    public R ccc(@RequestBody @Valid DeviceForm deviceForm, HttpServletRequest request) {
    	
    	System.out.println(deviceForm);
    	return R.ok();
    }
    
  
    @PostMapping("/sdevice")
    public R save(@RequestBody @Valid DeviceForm deviceForm, HttpServletRequest request) {

       // Device device = deviceService.findByMacAddress(deviceForm.getMacAddress()); //查询出
    	/**
    	 * 新修改的查询方法=》根据吗，mac地址以及条件平台为传入的平台
    	 */
        Device device = deviceService.findByMacAddressandpirfrm(deviceForm.getMacAddress(),deviceForm.getPlatform()); //查询出
        
        if (device == null){
            device = new Device();
        }
        
        BeanUtils.copyProperties(deviceForm,device);
        device.setIpAddress(request.getRemoteAddr());
        device.setRegisterType(1);
        device = deviceService.save(device);
        return R.ok(device);
    }
    
    
    @PostMapping("/queryrelevancefordeviceid")
    public R queryrelevancefordeviceid(Integer deviceid) {
    	List<Device> devices=deviceService.queryrelevancefordeviceid(deviceid);
        if(devices==null){
            return R.error("queryrelevancefordeviceid=> 设备集合新消息为空");
        }
    	System.out.println(devices);
    	return R.ok(devices);
    }
    
    
    @PutMapping("/{id}")
    public R update(@PathVariable Integer id,@RequestParam(value = "name") String name) {
        Device device = deviceService.findById(id);

        if(device==null){
            return R.error("put=> 设备新消息为空");
        }
        device.setName(name);
        device = deviceService.update(device);
        return R.ok(device);
    }
    
   
    //根据终端类型查询
    @PostMapping("/findAllByforplatform")
    public R findAllByforplatform(String platform){
    	
    	List<Device> devicelist=deviceService.findAllByforplatform(platform);

        if(devicelist==null){
            return R.error("findAllByforplatform=> 设备集合新消息为空");
        }

    	return R.ok(devicelist);
    }
      
    
    //新写的修改方法
    @GetMapping("/update")
    public R updateforid(@RequestParam(value = "id") int id,@RequestParam(value = "name") String name) {
    	Device device = deviceService.findById(id);

        if(device==null){
            return R.error("update=> 设备新消息为空");
        }
    	 device.setName(name);
    	 device = deviceService.update(device);
    	 
    	 
    	 return R.ok(device);
    }
    
    
    @PostMapping("/init/{id}")
    public R init(@PathVariable Integer id) {
        Device device = deviceService.findById(id);
        if(device==null){
            return R.error("update=> 设备新消息为空");
        }
        GlobalUser.channels.forEach(channel -> {
            if (channel.remoteAddress().toString().equals(device.getIpAddress())) {
              //  log.info("初始化数据:{}--{}",device.getIpAddress(),device.getMacAddress());
                Message message = new Message();
                message.setAction("init");
                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
                return;
            }
        });
        return R.ok();
    }

    @PostMapping("/init")
    public R initin(Integer id) {
        Device device = deviceService.findById(id);
        if(device==null){
            return R.error("update=> 设备新消息为空");
        }
        GlobalUser.channels.forEach(channel -> {
            if (channel.remoteAddress().toString().equals(device.getIpAddress())) {
                //  log.info("初始化数据:{}--{}",device.getIpAddress(),device.getMacAddress());
                Message message = new Message();
                message.setAction("init");
                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
                return;
            }
        });
        return R.ok();
    }
    
    
    
    @PostMapping("/deletedevice")
    public R deletedevice() {
    	
    	int r =deviceService.deletedevice();
    	if(r>0) {
    		return R.ok();
    	}else {
    		return R.error();
    	}
    }


}
