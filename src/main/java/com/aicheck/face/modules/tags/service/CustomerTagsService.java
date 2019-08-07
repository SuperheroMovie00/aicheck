/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.tags.service;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.tags.entity.CustomerTags;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:42 PM 2018/11/26
 */
public interface CustomerTagsService extends BaseService<CustomerTags,Integer> {

    List<CustomerTags> findByCustomerId(Integer customerId);

    int deleteByCustomerId(Integer customerId);
    
    int deletecustomertags(Integer customerId,Integer tagId);
    
    
}
