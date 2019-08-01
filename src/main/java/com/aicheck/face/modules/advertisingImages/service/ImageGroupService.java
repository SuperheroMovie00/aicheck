/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.advertisingImages.service;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.advertisingImages.entity.ImageGroup;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:20 PM 2019/2/18
 */
public interface ImageGroupService extends BaseService<ImageGroup,Integer> {

    List<ImageGroup> findByParent(Integer id);

    List<ImageGroup> findParentGroup();


    ImageGroup querydefault();

    List<ImageGroup> findallnotdefault();

    int updateDeviceIds(String deviceIds,Integer id);

    List<ImageGroup> deleteByIdIn(List<Integer> idList);
}
