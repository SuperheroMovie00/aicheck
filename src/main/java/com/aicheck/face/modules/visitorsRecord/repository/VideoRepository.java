package com.aicheck.face.modules.visitorsRecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.aicheck.face.modules.visitorsRecord.entity.Video;


public interface VideoRepository extends JpaRepository<Video, Integer>  {

	  @Query(value = "select * from video where create_time like '%?1%' ", nativeQuery = true)
	  Video findvideo(String date);
	    
	
	
}
