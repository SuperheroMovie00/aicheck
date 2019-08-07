/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.product.service;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.product.entity.Product;
import org.springframework.data.domain.Page;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:33 PM 2018/11/21
 */
public interface ProductService extends BaseService<Product,Integer> {

   Page<Product> findAll(Integer currentPage, Integer pageSize);

   String findLastCode();
}
