package com.aicheck.face.modules.pathseting.service.impl;

import com.aicheck.face.modules.pathseting.entity.pathseting;
import com.aicheck.face.modules.pathseting.repository.pathsetingRepository;
import com.aicheck.face.modules.pathseting.service.pathsetingService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("pathsetingService")
public class pathsetingServiceImpl implements pathsetingService {

    @Autowired
    private pathsetingRepository pathsetingRepository;

    @Override
    public pathseting save(pathseting pathseting) {
        return pathsetingRepository.save(pathseting);
    }

    @Override
    public List<pathseting> save(List<pathseting> t) {

        return pathsetingRepository.saveAll(t);
    }

    @Override
    public void delete(Integer integer) {
        pathsetingRepository.deleteById(integer);
    }

    @Override
    public pathseting update(pathseting pathseting) {
        return pathsetingRepository.save(pathseting);
    }

    @Override
    public List<pathseting> find(pathseting pathseting) {
        return null;
    }

    @Override
    public List<pathseting> findAll() {
        return pathsetingRepository.findAll();
    }

    @Override
    public pathseting findById(Integer integer) {
        Optional<pathseting> optionalProduct = pathsetingRepository.findById(integer);
        return optionalProduct.isPresent()?optionalProduct.get():null;
    }

    @Override
    public pathseting findpathfortype(String type) {
        return pathsetingRepository.findpathfortype(type);
    }
}
