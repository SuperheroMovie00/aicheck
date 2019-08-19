package com.aicheck.face.modules.system.repository;



import com.aicheck.face.modules.system.entity.SystemParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SystemParameterRepository extends JpaRepository<SystemParameter,Integer> {


    @Query(value = "select *from systems where status=1 and system_type=?1",nativeQuery = true)
    SystemParameter querysystem(String systemtype);
    
   

}
