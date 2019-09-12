package com.aicheck.face.modules.visitorsRecord.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.aicheck.face.modules.visitorsRecord.entity.Carstatistics;


public interface CarstatisticsRepository extends JpaRepository<Carstatistics, Integer> {
	
	@Query(value = "select * from carstatistics where carnumber=?1 and status=1", nativeQuery = true)
    Carstatistics selectcarstatisticsforcarnumber(String carnumber);
	
	@Query(value = "SELECT * FROM carstatistics where TO_DAYS(create_time) = TO_DAYS(NOW()) and status=1 order by create_time desc  LIMIT ?1,?2 ", nativeQuery = true)
    List<Carstatistics> querycarstatisforday(Integer currentPage, Integer pageSize);
	
	@Query(value = "SELECT * FROM carstatistics where TO_DAYS(create_time) = TO_DAYS(NOW()) order by create_time  desc LIMIT ?1,?2", nativeQuery = true)
    List<Carstatistics> querycarallforday(Integer currentPage, Integer pageSize);
	
	@Query(value = "SELECT COUNT(*) FROM carstatistics where TO_DAYS(create_time) = TO_DAYS(NOW())", nativeQuery = true)
    int querycarallfordaycount();
	
	@Query(value = "SELECT COUNT(*) FROM carstatistics where TO_DAYS(create_time) = TO_DAYS(NOW()) and status=1", nativeQuery = true)
    int querycarstatisfordaycount();
	
	
	@Modifying
    @Transactional
	@Query(value = "delete FROM carstatistics", nativeQuery = true)
    int deleteall();
	
}
