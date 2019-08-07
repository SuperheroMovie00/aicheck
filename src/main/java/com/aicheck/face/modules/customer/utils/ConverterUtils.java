/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.customer.utils;

import com.aicheck.face.common.utils.SpringContextUtils;
import com.aicheck.face.modules.customer.entity.Customer;
import com.aicheck.face.modules.customer.vo.CustomerVO;
import com.aicheck.face.modules.tags.entity.CustomerTags;
import com.aicheck.face.modules.tags.entity.Tags;
import com.aicheck.face.modules.tags.service.CustomerTagsService;
import com.aicheck.face.modules.tags.service.TagsService;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:22 PM 2019/2/25
 */
public class ConverterUtils {

    private static CustomerTagsService customerTagsService = (CustomerTagsService) SpringContextUtils.getBean("customerTagsService");
    private static TagsService tagsService = (TagsService) SpringContextUtils.getBean("tagsService");

    public static List<CustomerVO> converterCustomerVOS(List<Customer> customerList) {
        List<CustomerVO> customerVOList = com.aicheck.face.common.utils.BeanUtils.batchTransform(CustomerVO.class,customerList);

        for (CustomerVO customerVO : customerVOList) {

            Integer customerId = Integer.parseInt(customerVO.getCustomerId());

            List<CustomerTags> customerTagsList = customerTagsService.findByCustomerId(customerId);
            if (!CollectionUtils.isEmpty(customerTagsList)) {
                List<Tags> tagsList = tagsService.findByIdIn(customerTagsList.stream().map(CustomerTags::getTagId).collect(Collectors.toList()));
                customerVO.setTags(tagsList);
            }

        }

        return customerVOList;
    }

    public static CustomerVO converterCustomerVO(Customer customer) {
        CustomerVO customerVO = new CustomerVO();
        BeanUtils.copyProperties(customer,customerVO);

        List<CustomerTags> customerTagsList = customerTagsService.findByCustomerId(customer.getId());
        if (!CollectionUtils.isEmpty(customerTagsList)) {
            List<Tags> tagsList = tagsService.findByIdIn(customerTagsList.stream().map(CustomerTags::getTagId).collect(Collectors.toList()));
            customerVO.setTags(tagsList);
        }

        return customerVO;
    }

}
