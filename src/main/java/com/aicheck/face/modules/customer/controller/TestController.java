/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.customer.controller;

import com.aicheck.face.common.utils.ExcelUtil;
import com.aicheck.face.modules.customer.entity.Customer;
import com.aicheck.face.modules.customer.service.CustomerService;
import com.aicheck.face.modules.nettyPush.GlobalUser;
import com.aicheck.face.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:12 PM 2019/2/21
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/export")
    public R exportExcel(HttpServletResponse response) {
        String[] s = {"编号","名称","年龄","性别","手机","邮箱","地址","创建时间","创建编号","用户JSON","头像","脸部特征"};
        List<String> header = Arrays.asList(s);

        List<Customer> customerList = customerService.findAll();
        if (!CollectionUtils.isEmpty(customerList)) {
            List<List<String>> dataList = new ArrayList<>();
            customerList.forEach(customer -> {
                List<String> objectList = new ArrayList<>();
                objectList.add(String.valueOf(customer.getId()));
                objectList.add(customer.getName());
                objectList.add(String.valueOf(customer.getAge()));
                objectList.add(customer.getGender());
                objectList.add(customer.getMobile());
                objectList.add(customer.getEmail());
                objectList.add(customer.getAddress());
                objectList.add(String.valueOf(customer.getCreateTime()));
                objectList.add(String.valueOf(customer.getCreateId()));
                objectList.add(customer.getUserModelValue());
                objectList.add(customer.getImgUrl());
                objectList.add(customer.getFaceId());
                dataList.add(objectList);
            });
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fileName = simpleDateFormat.format(new Date());

            try {

                ExcelUtil.exportExcel(response,fileName,"contact",header,dataList,null);
                log.error("导出excel文档:成功");
            } catch (Exception e) {

                log.error("导出excel文档错误:" + e);
            }
        }

        return R.ok();
    }

//    @PostMapping("/import")
//    public R importExcel(MultipartFile file) {
//        System.out.println("导入");
//        try {
//            List<Customer> customerList = ExcelUtil.importExcel(file.getOriginalFilename(),file);
//            System.out.println("===================================");
//            //TODO 批量新增至数据库
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return R.ok();
//    }

    @PostMapping("check")
    public R pushBoxCheck() {

        GlobalUser.channels.forEach(channel ->{
            System.out.println(channel.id());
        });

        return R.ok();
    }
}
