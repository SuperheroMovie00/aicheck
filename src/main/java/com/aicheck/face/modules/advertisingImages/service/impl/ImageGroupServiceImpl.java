/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.advertisingImages.service.impl;

import com.aicheck.face.modules.advertisingImages.entity.ImageGroup;
import com.aicheck.face.modules.advertisingImages.repository.ImageGroupRepository;
import com.aicheck.face.modules.advertisingImages.service.ImageGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:20 PM 2019/2/18
 */
@Service
public class ImageGroupServiceImpl implements ImageGroupService {
    @Autowired
    private ImageGroupRepository imageGroupRepository;
    @Override
    public ImageGroup save(ImageGroup imageGroup) {
        return imageGroupRepository.save(imageGroup);
    }

    @Override
    public List<ImageGroup> save(List<ImageGroup> t) {
        return imageGroupRepository.saveAll(t);
    }

    @Override
    public void delete(Integer integer) {
        imageGroupRepository.deleteById(integer);
    }

    @Override
    public ImageGroup update(ImageGroup imageGroup) {
        return imageGroupRepository.save(imageGroup);
    }

    @Override
    public List<ImageGroup> find(ImageGroup imageGroup) {
        return null;
    }

    @Override
    public List<ImageGroup> findAll() {

        return imageGroupRepository.findAll();
    }

    @Override
    public ImageGroup findById(Integer integer) {
        Optional<ImageGroup> optional = imageGroupRepository.findById(integer);

        return optional.isPresent()?optional.get():null;
    }

    @Override
    public List<ImageGroup> findByParent(Integer id) {

        return imageGroupRepository.findByParentId(id);
    }

    @Override
    public List<ImageGroup> findParentGroup() {
        return imageGroupRepository.findParentGroup();
    }


    @Override
    public ImageGroup querydefault() {
        return imageGroupRepository.querydefault();
    }

    @Override
    public List<ImageGroup> findallnotdefault() {
        return imageGroupRepository.findallnotdefault();
    }

    @Override
    public int updateDeviceIds(String deviceIds, Integer id) {

        return imageGroupRepository.updateDeviceIds(deviceIds,id);
    }

    @Override
    public List<ImageGroup> deleteByIdIn(List<Integer> idList) {
        return imageGroupRepository.deleteByIdIn(idList);
    }
}
