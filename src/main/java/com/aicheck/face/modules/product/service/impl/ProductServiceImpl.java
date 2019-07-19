/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.product.service.impl;


import com.aicheck.face.modules.product.entity.Product;
import com.aicheck.face.modules.product.repository.ProductRepository;
import com.aicheck.face.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:33 PM 2018/11/21
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product save(Product product) {

        product.setCreateId(1);
        return productRepository.save(product);
    }

    @Override
    public List<Product> save(List<Product> t) {
        t.forEach(product -> {
            product.setCreateId(1);
        });
        return productRepository.saveAll(t);
    }

    @Override
    public void delete(Integer integer) {
        productRepository.deleteById(integer);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> find(Product product) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Integer integer) {
        Optional<Product> optionalProduct = productRepository.findById(integer);
        return optionalProduct.isPresent()?optionalProduct.get():null;
    }

    @Override
    public Page<Product> findAll(Integer currentPage, Integer pageSize) {
        Pageable pageable = PageRequest.of(currentPage -1,pageSize,Sort.Direction.DESC,"createTime");
        return productRepository.findAll(pageable);
    }

    @Override
    public String findLastCode() {

        return productRepository.findLastCode();
    }
}
