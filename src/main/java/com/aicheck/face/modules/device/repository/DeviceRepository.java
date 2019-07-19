/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.device.repository;

import com.aicheck.face.modules.device.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:49 PM 2019/1/25
 */
@SuppressWarnings("rawtypes")
public interface DeviceRepository extends JpaRepository<Device,Integer>,JpaSpecificationExecutor {

    Device findByMacAddress(String macAddress);

    @Query(value="select *from device where mac_address=?1",nativeQuery = true)
    Device findByMacAddresser(String macAddress);

    
    //新修改的查询方法=》根据吗，mac地址以及条件平台为传入的平台
    @Query(value="select *from device where mac_address=?1 and platform=?2",nativeQuery = true)
    Device findByMacAddressandpirfrm(String macAddress,String platform);

    List<Device> findByPlatform(String platform);

    @Query(value = "select * from device where id not in ( select * from ( SELECT distinct " +
            "substring_index( substring_index( a.device_ids, ',', b.help_topic_id + 1 ), ',',- 1 ) as device_id FROM image_group a " +
            "JOIN mysql.help_topic b ON b.help_topic_id < ( length( a.device_ids ) - length( REPLACE ( a.device_ids, ',', '' ) ) + 1 )  ORDER BY a.id)c where c.device_id is not null and c.device_id !='' )",nativeQuery = true)
    List<Device> findByNotGroupDevice();

    
    @Query(value = "select * from device where register_type is null or register_type = ''",nativeQuery = true)
    List<Device> findAllByRegisterType();
    

    
    @Query(value = "select * from device where platform=?1",nativeQuery = true)
    List<Device> findAllByforplatform(String platform);
    
    
    @Query(value = "select * from device where ip_address=?1",nativeQuery = true)
    Device findAllByforipaddress(String ip_address);
    
    
    @Query(value = "select * from device where platform='box' and is_relevance=0 and ip_address<>?1",nativeQuery = true)
    List<Device> deviceRepository(String deviceip);
    
    @Query(value ="select *from device where id in (select boxdevice_id from relevance where aidevice_id=?1)",nativeQuery = true)
	List<Device> queryrelevancefordeviceid(Integer id);
    
    
    @Query(value ="select *from device where platform=?1 and mac_address=?2",nativeQuery = true)
	Device finddeviceforplatformandmacaddress(String platform ,String macaddress);
    
}
