/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.customer.service.impl;

import com.aicheck.face.modules.customer.entity.Customer;
import com.aicheck.face.modules.customer.repository.CustomerRepository;
import com.aicheck.face.modules.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:25 PM 2018/11/21
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer save(Customer customer) {
        customer.setCreateId(1);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> save(List<Customer> t) {
        t.forEach(customer -> {
            customer.setCreateId(1);
        });
        return customerRepository.saveAll(t);
    }

    @Override
    public void delete(Integer integer) {

        customerRepository.deleteById(integer);
    }

    @Override
    public void delete(List<Integer> idList) {

        customerRepository.deleteByIdIn(idList);
    }

    @Override
    public Customer findByMobile(String mobile) {
        return customerRepository.findByMobile(mobile);
    }

    @Override
    public Customer update(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> find(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(Integer integer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(integer);
        return optionalCustomer.isPresent()?optionalCustomer.get():null;
    }

    @Override
    public Page<Customer> findAll(Integer currentPage, Integer pageSize) {

        Pageable pageable = PageRequest.of(currentPage -1,pageSize,Sort.Direction.DESC,"createTime");

        return customerRepository.findAll(pageable);
    }

    @Override
    public List<Customer> findByIdIn(Integer[] ids) {

        List<Customer> customers = customerRepository.findByIdIn(Arrays.asList(ids));

        return customers;
    }

    @Override
    public String findByMaxCode() {
        return customerRepository.findByMaxCode();
    }

}
