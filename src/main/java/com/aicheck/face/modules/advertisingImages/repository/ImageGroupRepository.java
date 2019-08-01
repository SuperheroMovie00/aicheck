/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.advertisingImages.repository;

import com.aicheck.face.modules.advertisingImages.entity.ImageGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:19 PM 2019/2/18
 */
public interface ImageGroupRepository extends JpaRepository<ImageGroup,Integer> {

    @Transactional
    List<ImageGroup> deleteByIdIn(List<Integer> idList);

    List<ImageGroup> findByParentId(Integer parentId);

    @Query(value = "select *from image_group where defaults='default'",nativeQuery = true)
    ImageGroup querydefault();

    @Query(value = "select * from image_group where parent_id is null or parent_id = ''",nativeQuery = true)
    List<ImageGroup> findParentGroup();

    @Query(value = "select * from image_group where defaults <> 'default'",nativeQuery = true)
    List<ImageGroup> findallnotdefault();


    @Modifying
    @Transactional
    @Query(value = "update image_group set device_ids = ?1 where id = ?2",nativeQuery = true)
    int updateDeviceIds(String deviceIds,Integer id);
}
