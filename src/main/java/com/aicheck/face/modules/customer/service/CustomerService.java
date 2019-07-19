/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.customer.service;


import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.customer.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.List;


/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:25 PM 2018/11/21
 */
public interface CustomerService extends BaseService<Customer,Integer> {

    Page<Customer> findAll(Integer currentPage, Integer pageSize);

    List<Customer> findByIdIn(Integer[] ids);

    String findByMaxCode();

    void delete(List<Integer> ids);

    Customer findByMobile(String mobile);
    
    


}
