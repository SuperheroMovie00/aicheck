/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.advertisingImages.service.impl;


import com.aicheck.face.modules.advertisingImages.entity.AdvertisingImages;
import com.aicheck.face.modules.advertisingImages.repository.AdvertisingImagesRepository;
import com.aicheck.face.modules.advertisingImages.service.AdvertisingImagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 1:38 PM 2019/1/21
 */
@Service("advertisingImagesService")
@Slf4j
public class AdvertisingImagesServiceImpl implements AdvertisingImagesService {
    @Autowired
    private AdvertisingImagesRepository advertisingImagesRepository;

    @Override
    public AdvertisingImages save(AdvertisingImages advertisingImages) {
        return advertisingImagesRepository.save(advertisingImages);
    }

    @Override
    public List<AdvertisingImages> save(List<AdvertisingImages> t) {
        return advertisingImagesRepository.saveAll(t);
    }

    @Override
    public void delete(Integer integer) {
        advertisingImagesRepository.deleteById(integer);
    }

    @Override
    public AdvertisingImages update(AdvertisingImages advertisingImages) {
        return advertisingImagesRepository.save(advertisingImages);
    }

    @Override
    public List<AdvertisingImages> find(AdvertisingImages advertisingImages) {
        return null;
    }

    @Override
    public List<AdvertisingImages> findAll() {
        return advertisingImagesRepository.findAll();
    }

    @Override
    public AdvertisingImages findById(Integer integer) {
        Optional<AdvertisingImages> optional = advertisingImagesRepository.findById(integer);
        return optional.isPresent()?optional.get():null;
    }

    @Override
    public List<AdvertisingImages> findByGroupId(Integer group) {

        return advertisingImagesRepository.findByGroupIdOrderBySortAsc(group);
    }

    @Override
    public AdvertisingImages findByGroupIdSortAsc(Integer groupId) {
        return advertisingImagesRepository.findByGroupIdOrderBySortAscLimit(groupId);
    }

    @Override
    public void delete(List<Integer> idList) {
        advertisingImagesRepository.deleteByIdIn(idList);
    }

    @Override
    public List<AdvertisingImages> findByDeviceId(Integer deviceId) {

        return advertisingImagesRepository.findByDeviceId(deviceId);
    }

    @Override
    public void deleteByGroupId(Integer groupId) {
        advertisingImagesRepository.deleteByGroupId(groupId);
    }

    @Override
    public List<AdvertisingImages> findByIds(List<Integer> idList) {

        return advertisingImagesRepository.findByIdIn(idList);
    }

    @Override
    public List<AdvertisingImages> findByDeviceIdAi(String deviceId) {

        return advertisingImagesRepository.findByDeviceIdAi(deviceId);
    }

	@Override
	public List<AdvertisingImages> qyeryadvertisingimagesforstrategyid(Integer strategyid) {
		// TODO Auto-generated method stub
		return advertisingImagesRepository.qyeryadvertisingimagesforstrategyid(strategyid);
	}
	
	
	@Override
	public List<AdvertisingImages> qyeryadvertisingimagesforgroupid(Integer groupid) {
		// TODO Auto-generated method stub
		System.out.println("service取到的资源=>"+advertisingImagesRepository.qyeryadvertisingimagesforgroupid(groupid));
		return advertisingImagesRepository.qyeryadvertisingimagesforgroupid(groupid);
	}

	@Override
	public List<AdvertisingImages> qyeryadvertisingimagesforgroupidwherezero() {
		// TODO Auto-generated method stub
		return advertisingImagesRepository.qyeryadvertisingimagesforgroupidwherezero();
	}

}
