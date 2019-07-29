/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.product.controller;

import com.aicheck.face.common.constant.ResultEnum;
import com.aicheck.face.common.utils.ExcelUtil;
import com.aicheck.face.common.utils.PrimaryGenerate;
import com.aicheck.face.common.utils.UpdateUtils;
import com.aicheck.face.modules.product.entity.Product;
import com.aicheck.face.modules.product.entity.ProductForm;
import com.aicheck.face.modules.product.service.ProductService;
import com.aicheck.face.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:35 PM 2018/11/21
 */
@RestController
@RequestMapping("/v1/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public R findAllList(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                         @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize) {
        Page<Product> page = productService.findAll(currentPage,pageSize);
        if(page==null){
            return R.error("/v1/product/get=>page为空");
        }

        return R.ok(page.getContent());
    }
    @GetMapping("/{id}")
    public R findById(@PathVariable Integer id) {

        Product product = productService.findById(id);
        if(product==null){
            return R.error("/v1/product//{id}=>product为空");
        }
        return R.ok(product);
    }
    @PostMapping
    public R save(@RequestBody @Valid ProductForm productForm) {

        Product product = new Product();
        BeanUtils.copyProperties(productForm,product);
        String code = productService.findLastCode();
        if(code==null){
            return R.error("/v1/product//{id}=>code为空");
        }
        String newCode = PrimaryGenerate.getInstance().generaterNextNumber(code);
        product.setCode(newCode);
        product = productService.save(product);

        return R.ok(product);
    }
    @PutMapping("/{id}")
    public R update(@PathVariable Integer id, @RequestBody @Valid ProductForm productForm) {
        Product product = productService.findById(id);
        if (product == null) {
            return R.error(ResultEnum.IS_NOT_EXIST.getCode(),ResultEnum.IS_NOT_EXIST.getMsg());
        }
        UpdateUtils.copyNullProperties(productForm,product);
        Product result = productService.update(product);
        return R.ok(result);
    }
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        productService.delete(id);
        return R.ok();
    }

    @RequestMapping(value = "/export")
    public void exportExcel(HttpServletResponse response) {
        String[] s = {"商品编码","商品名称","类型","价格","制造商","生产日期","描述","备注","创建时间","创建人","商品图片"};
        List<String> header = Arrays.asList(s);

        List<Product> productList = productService.findAll();
        if (!CollectionUtils.isEmpty(productList)) {
            List<List<String>> dataList = new ArrayList<>();
            productList.forEach(product -> {
                List<String> objectList = new ArrayList<>();
//                objectList.add(String.valueOf(product.getId()));
                objectList.add(product.getCode());
                objectList.add(product.getName());
                objectList.add(product.getType());
                objectList.add(String.valueOf(product.getPrice()));
                objectList.add(String.valueOf(product.getManufacturers()));
                objectList.add(String.valueOf(product.getDateOfProduction()));
                objectList.add(String.valueOf(product.getDescription()));
                objectList.add(String.valueOf(product.getRemark()));
                objectList.add(String.valueOf(product.getCreateTime()));
                objectList.add(String.valueOf(product.getCreateId()));
                objectList.add(product.getImgUrl());
                dataList.add(objectList);
            });
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fileName = simpleDateFormat.format(new Date());

            try {

                ExcelUtil.exportExcel(response,fileName,"contact",header,dataList,null);

            } catch (Exception e) {

                log.error("导出excel文档错误:" + e);
            }
        }
    }

    @PostMapping("/import")
    public R importExcel(MultipartFile file) {
        try {
            List<Product> productList = ExcelUtil.importProductExcel(file.getOriginalFilename(),file);

            productService.save(productList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok();
    }
}
