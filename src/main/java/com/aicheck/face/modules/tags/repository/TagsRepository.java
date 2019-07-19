/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.tags.repository;

import com.aicheck.face.modules.tags.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:40 PM 2018/11/26
 */
public interface TagsRepository extends JpaRepository<Tags,Integer> {

    List<Tags> findByIdIn(List<Integer> idList);
    
    @Query(value = "select b.id,b.`name`,b.sort,b.create_id,b.create_time from customer_tags a INNER JOIN tags b ON a.tag_id=b.id where a.customer_id=?1 ",nativeQuery = true)
    List<Tags> querytagsforcustomerid(Integer customerid);
    
    

}
