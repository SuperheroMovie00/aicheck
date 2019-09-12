package com.aicheck.face.modules.visitorsRecord.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.customer.entity.Customer;
import com.aicheck.face.modules.visitorsRecord.entity.Carstatistics;


public interface CarstatisticsService extends BaseService<Carstatistics,Integer> {

	Carstatistics selectcarstatisticsforcarnumber(String carnumber);
	
	List<Carstatistics> querycarstatisforday(Integer currentPage, Integer pageSize);
	
	List<Carstatistics> querycarallforday(Integer currentPage, Integer pageSize);
	
	int querycarallfordaycount();
	
	Page<Carstatistics> findAll(Integer currentPage, Integer pageSize);
	 
	int querycarstatisfordaycount();
	 
	int deleteall();
	
}
