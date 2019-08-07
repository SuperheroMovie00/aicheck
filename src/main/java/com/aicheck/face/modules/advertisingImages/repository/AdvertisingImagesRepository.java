/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.advertisingImages.repository;

import com.aicheck.face.modules.advertisingImages.entity.AdvertisingImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 1:35 PM 2019/1/21
 */
public interface AdvertisingImagesRepository extends JpaRepository<AdvertisingImages,Integer> {

    @Transactional
    List<AdvertisingImages> deleteByIdIn(List<Integer> idList);

    @Query(value = "select * from advertising_images where group_id = ?1 order by sort asc limit 1",nativeQuery = true)
    AdvertisingImages findByGroupIdOrderBySortAscLimit(Integer groupId);

    @Transactional
    void deleteByGroupId(Integer groupId);
    
    //根据设备id查询资源
    /*@Query(value = "select ai.* from image_group ig inner join advertising_images ai on ig.id = ai.group_id where find_in_set(?1,ig.device_ids) > 0 order by ai.sort asc",nativeQuery = true)
    List<AdvertisingImages> findByDeviceId(Integer deviceId);*/
    
    //新修改的根据设备id(Integer)查询资源
    @Query(value = "select *from advertising_images where group_id in ( select id from image_group where find_in_set((select mac_address from device where id=?1),device_ids) )",nativeQuery = true)
    List<AdvertisingImages> findByDeviceId(Integer deviceId);
    
    List<AdvertisingImages> findByGroupIdOrderBySortAsc(Integer group);

    List<AdvertisingImages> findByIdIn(List<Integer> ids);

    //原来的根据mac地址查询资源
    /*@Query(value = "select ai.* from device d inner join image_group ig on find_in_set(d.id,ig.device_ids) > 0 inner join advertising_images ai on ig.id = ai.group_id and ai.strategy_id=0 where d.mac_address = ?1",nativeQuery = true)
    List<AdvertisingImages> findByDeviceIdAi(String deviceId);*/
    
    //新修改的根据mac地址查询资源
    @Query(value = "select *from advertising_images where group_id in(SELECT a.id from image_group a where device_ids LIKE %?1%)",nativeQuery = true)
    List<AdvertisingImages> findByDeviceIdAi(String deviceId);
    
    @Query(value = "select * from advertising_images where strategy_id=?1",nativeQuery = true)
    List<AdvertisingImages> qyeryadvertisingimagesforstrategyid(Integer strategyid);
    
    
    @Query(value = "select * from advertising_images where group_id=?1",nativeQuery = true)
    List<AdvertisingImages> qyeryadvertisingimagesforgroupid(Integer groupid);
    
    @Query(value = "select * from advertising_images where group_id=0",nativeQuery = true)
    List<AdvertisingImages> qyeryadvertisingimagesforgroupidwherezero();
    
    
    

}
