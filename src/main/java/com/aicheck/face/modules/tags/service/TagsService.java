/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.tags.service;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.tags.entity.Tags;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:42 PM 2018/11/26
 */
public interface TagsService extends BaseService<Tags,Integer> {

    Page<Tags> findAllList(Integer currentPage, Integer pageSize);

    List<Tags> findByIdIn(List<Integer> idList);
    
    List<Tags> querytagsforcustomerid(Integer customerid);
    
}
