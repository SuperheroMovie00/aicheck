/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.tags.service.impl;

import com.aicheck.face.modules.tags.entity.CustomerTags;
import com.aicheck.face.modules.tags.repository.CustomerTagsRepository;
import com.aicheck.face.modules.tags.service.CustomerTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:42 PM 2018/11/26
 */
@Service("customerTagsService")
public class CustomerTagsServiceImpl implements CustomerTagsService {
    @Autowired
    private CustomerTagsRepository customerTagsRepository;

    @Override
    public CustomerTags save(CustomerTags customerTags) {
        return customerTagsRepository.save(customerTags);
    }

    @Override
    public List<CustomerTags> save(List<CustomerTags> t) {
        return customerTagsRepository.saveAll(t);
    }

    @Override
    public void delete(Integer integer) {
        customerTagsRepository.deleteById(integer);
    }

    @Override
    public CustomerTags update(CustomerTags customerTags) {
        return customerTagsRepository.save(customerTags);
    }

    @Override
    public List<CustomerTags> find(CustomerTags customerTags) {
        return null;
    }

    @Override
    public List<CustomerTags> findAll() {
        return customerTagsRepository.findAll();
    }

    @Override
    public CustomerTags findById(Integer integer) {
        Optional<CustomerTags> optionalCustomerTags  = customerTagsRepository.findById(integer);
        return optionalCustomerTags.isPresent()?optionalCustomerTags.get():null;
    }

    @Override
    public List<CustomerTags> findByCustomerId(Integer customerId) {
        return customerTagsRepository.findByCustomerId(customerId);
    }

    @Override
    public int deleteByCustomerId(Integer customerId) {
        return customerTagsRepository.deleteByCustomerId(customerId);
    }

    
	@Override
	public int deletecustomertags(Integer customerId, Integer tagId) {
		
		return customerTagsRepository.deletecustomertags(customerId, tagId);
	}
}
