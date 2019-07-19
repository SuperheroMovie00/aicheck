/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.tags.repository;

import com.aicheck.face.modules.tags.entity.CustomerTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:41 PM 2018/11/26
 */
public interface CustomerTagsRepository extends JpaRepository<CustomerTags,Integer> {

    List<CustomerTags> findByCustomerId(Integer customerId);

    @Transactional
    int deleteByCustomerId(Integer customerId);
    
    @Modifying
    @Transactional
    @Query(value = "delete from customer_tags where customer_id=?1 and tag_id=?2",nativeQuery = true)
    int deletecustomertags(Integer customerId,Integer tagId);
    
}
