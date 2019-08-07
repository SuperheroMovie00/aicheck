/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.advertisingImages.service;


import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.advertisingImages.entity.AdvertisingImages;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 1:34 PM 2019/1/21
 */
public interface AdvertisingImagesService extends BaseService<AdvertisingImages,Integer> {

    List<AdvertisingImages> findByGroupId(Integer group);


    AdvertisingImages findByGroupIdSortAsc(Integer groupId);

    void delete(List<Integer> idList);

    List<AdvertisingImages> findByDeviceId(Integer deviceId);

    void deleteByGroupId(Integer groupId);

    List<AdvertisingImages> findByIds(List<Integer> idList);

    List<AdvertisingImages> findByDeviceIdAi(String deviceId);
    
    List<AdvertisingImages> qyeryadvertisingimagesforstrategyid(Integer strategyid);

    List<AdvertisingImages> qyeryadvertisingimagesforgroupid(Integer groupid);
    
    List<AdvertisingImages> qyeryadvertisingimagesforgroupidwherezero();

}
