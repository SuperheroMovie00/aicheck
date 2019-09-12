package com.aicheck.face.modules.visitorsRecord.repository;

import com.aicheck.face.modules.visitorsRecord.entity.Videostatistic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VideostatisticRepository extends JpaRepository<Videostatistic, Integer> {


    @Query(value = "select * from videostatistic where date_type=?1 and TO_DAYS(create_time) = TO_DAYS(NOW())", nativeQuery = true)
    Videostatistic findvideostatisicfortype(String type);
    
    @Query(value = "select * from videostatistic where date_type='day' and create_time like %?1%", nativeQuery = true)
    Videostatistic findvideostatisicfordate(String date);
    
    @Query(value = "SELECT * FROM `videostatistic` where create_time like %?1% and date_type='day' ORDER BY create_time asc", nativeQuery = true)
    List<Videostatistic> findvideostatisicfordaylist(String datestr);
    
    
    @Query(value = "SELECT * FROM `videostatistic` where  date_type='week' and  create_time > ?1 ORDER BY create_time asc LIMIT 7", nativeQuery = true)
    List<Videostatistic> findvideostatisicforweeklist(String datestr);
    
    @Query(value = "SELECT * FROM `videostatistic` where  date_type='year' and create_time like  %?1%", nativeQuery = true)
    List<Videostatistic> findvideostatisicforyearlist(String datestr);
    
    @Query(value = "SELECT * FROM `videostatistic` where  date_type='week' and create_time like  %?1%", nativeQuery = true)
    Videostatistic findvideostatisicforweekyanlist(String datestr);

    @Modifying
    @Transactional
    @Query(value = "delete from `videostatistic` where create_time like  %?1% and date_type=?2", nativeQuery = true)
    int deletevideostatistic(String datestr,String type);







}
