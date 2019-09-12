package com.aicheck.face.modules.visitorsRecord.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aicheck.face.modules.visitorsRecord.entity.Carstatistics;

import com.aicheck.face.modules.visitorsRecord.repository.CarstatisticsRepository;
import com.aicheck.face.modules.visitorsRecord.service.CarstatisticsService;

@Service("carstatisticsService")
public class CarstatisticsServiceimpl implements CarstatisticsService {

	@Autowired 
	private CarstatisticsRepository CarstatisticsRepository;
	
	@Override
	public Carstatistics save(Carstatistics t) {
		// TODO Auto-generated method stub
		return CarstatisticsRepository.save(t);
	}

	@Override
	public List<Carstatistics> save(List<Carstatistics> t) {
		// TODO Auto-generated method stub
		return CarstatisticsRepository.saveAll(t);
	}

	@Override
	public void delete(Integer id) {
		CarstatisticsRepository.deleteById(id);
		
	}

	@Override
	public Carstatistics update(Carstatistics t) {
		// TODO Auto-generated method stub
		return CarstatisticsRepository.save(t);
	}

	@Override
	public List<Carstatistics> find(Carstatistics t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Carstatistics> findAll() {
		// TODO Auto-generated method stub
		return CarstatisticsRepository.findAll();
	}

	@Override
	public Carstatistics findById(Integer id) {
		Optional<Carstatistics> optionalVisitorsRecord = CarstatisticsRepository.findById(id);
        return optionalVisitorsRecord.isPresent() ? optionalVisitorsRecord.get() : null;
	}

	@Override
	public Carstatistics selectcarstatisticsforcarnumber(String carnumber) {
		// TODO Auto-generated method stub
		return CarstatisticsRepository.selectcarstatisticsforcarnumber(carnumber);
	}

	@Override
	public List<Carstatistics> querycarstatisforday(Integer currentPage, Integer pageSize) {
		// TODO Auto-generated method stub
		int pagenum=(currentPage-1)*pageSize;
		return CarstatisticsRepository.querycarstatisforday(pagenum,  pageSize);
	}

	@Override
	public Page<Carstatistics> findAll(Integer currentPage, Integer pageSize) {
	      Pageable pageable = PageRequest.of(currentPage -1,pageSize,Sort.Direction.DESC,"createTime");

	        return CarstatisticsRepository.findAll(pageable);
	}

	@Override
	public int querycarstatisfordaycount() {
		// TODO Auto-generated method stub
		return CarstatisticsRepository.querycarstatisfordaycount();
	}

	@Override
	public List<Carstatistics> querycarallforday(Integer currentPage, Integer pageSize) {
		// TODO Auto-generated method stub]
		int pagenum=(currentPage-1)*pageSize;
		return CarstatisticsRepository.querycarallforday(pagenum, pageSize);
	}

	@Override
	public int querycarallfordaycount() {
		// TODO Auto-generated method stub
		return CarstatisticsRepository.querycarallfordaycount();
	}

	@Override
	public int deleteall() {
		// TODO Auto-generated method stub
		return CarstatisticsRepository.deleteall();
	}

}
