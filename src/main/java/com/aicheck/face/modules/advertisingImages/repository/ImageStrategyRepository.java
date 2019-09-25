package com.aicheck.face.modules.advertisingImages.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aicheck.face.modules.advertisingImages.entity.ImageStrategy;

public interface ImageStrategyRepository extends JpaRepository<ImageStrategy,Integer> {

	
	 @Query(value = "select * from image_strategy where group_id=?1",nativeQuery = true)
	 List<ImageStrategy> querystrategyforcusid(Integer groupid);
	 
	 
	
}
