/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.product.repository;

import com.aicheck.face.modules.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:33 PM 2018/11/21
 */
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query(value = "select max(code) from product",nativeQuery = true)
    String findLastCode();
}
