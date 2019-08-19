package com.aicheck.face.modules.system.service.impl;


import com.aicheck.face.modules.system.entity.SystemParameter;
import com.aicheck.face.modules.system.repository.SystemParameterRepository;
import com.aicheck.face.modules.system.service.SystemParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("systemparameterservice")
public class SystemParameterServiceimpl implements SystemParameterService {

    @Autowired
    private SystemParameterRepository systemparameterrepository;


    @Override
    public SystemParameter save(SystemParameter systemParameter) {
        return systemparameterrepository.save(systemParameter);
    }

    @Override
    public List<SystemParameter> save(List<SystemParameter> t) {
        return systemparameterrepository.saveAll(t);
    }

    @Override
    public void delete(Integer integer) {
        systemparameterrepository.deleteById(integer);
    }

    @Override
    public SystemParameter update(SystemParameter systemParameter) {
        return systemparameterrepository.save(systemParameter);
    }

    @Override
    public List<SystemParameter> find(SystemParameter systemParameter) {
        return null;
    }

    @Override
    public List<SystemParameter> findAll() {
        return systemparameterrepository.findAll();
    }

    @Override
    public SystemParameter findById(Integer integer) {
        Optional<SystemParameter> optionalCustomer = systemparameterrepository.findById(integer);
        return optionalCustomer.isPresent()?optionalCustomer.get():null;
    }

    @Override
    public SystemParameter querysystem(String systemtype) {
        return systemparameterrepository.querysystem(systemtype);
    }
}
