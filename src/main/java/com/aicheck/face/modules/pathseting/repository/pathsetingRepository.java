package com.aicheck.face.modules.pathseting.repository;

import com.aicheck.face.modules.pathseting.entity.pathseting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface pathsetingRepository extends JpaRepository<pathseting,Integer> {

    @Query(value = "select * from pathseting where type=?1", nativeQuery = true)
    pathseting  findpathfortype(String type);

}
