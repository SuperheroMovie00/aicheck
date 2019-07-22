/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.device.service;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.device.entity.Device;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:50 PM 2019/1/25
 */
public interface DeviceService extends BaseService<Device,Integer> {

    Device findByMacAddresser(String macAddress);

    Device findByMacAddress(String macAddress);
    //新修改的查询方法=》根据吗，mac地址以及条件平台为传入的平台
    Device findByMacAddressandpirfrm(String macAddress,String platform);

    List<Device> findByPlatform(String platform);

    Page<Device> findAll(Integer currentPage,Integer pageSize,String platform);

    List<Device> findAllByRegisterType();

    List<Device> findAllByRegisterTypeonlybox();

    List<Device> findByNotGroupDevice();
    
    List<Device> findAllByforplatform(String platform);
    
    Device findAllByforipaddress(String ip_address);
    
    List<Device> querydevicefrobox(String deviceip);
    
    List<Device> queryrelevancefordeviceid(Integer id);
    
    Device finddeviceforplatformandmacaddress(String platform ,String macaddress);

    Device findAllByformacaddress(String mac_address);
    

}
